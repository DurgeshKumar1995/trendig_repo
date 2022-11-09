package com.repo.trending.ui.filter.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.annotation.CheckResult
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.repo.trending.databinding.ActivityFilterBinding
import com.repo.trending.model.Repo
import com.repo.trending.ui.filter.adapter.FilterAdapter
import com.repo.trending.ui.filter.view_model.FilterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFilterBinding

    private val adapter by lazy { FilterAdapter { actionOnItemClick(it) } }

    private val viewModel by viewModel<FilterViewModel>()
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityFilterBinding.inflate(layoutInflater).also { binding =it }.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.recyclerView.adapter = adapter


        binding.edtTitle.textChanges().debounce(300)
            .onEach {
                binding.progressBar.isVisible = true
                viewModel.getFilterData(it.toString().trim())
            }
            .launchIn(lifecycleScope)

        viewModel.dataList.observe(this){
            it?.run {
                binding.progressBar.isVisible = false
                adapter.setList(this)
                if (isNullOrEmpty()){
                    binding.txtEmpty.isVisible = true
                    binding.recyclerView.isVisible = true
                }else{
                    binding.txtEmpty.isVisible = false
                    binding.recyclerView.isVisible = true
                }
            }
        }
        viewModel.refreshPoint.observe(this){
            it?.run {
                binding.progressBar.isVisible = false
            }
        }

        viewModel.recordCount.observe(this){
            it?.run {
               binding.edtTitle.isEnabled =  (this >0)
               binding.txtEmpty.isVisible =  (this <1)
            }
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun actionOnItemClick(repo: Repo? = null) {
        if (repo!=null) {
            binding.progressBar.isVisible = true
            repo.isChecked = true
            viewModel.updateRepo(repo)
        }
    }
}

@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = doOnTextChanged { text, _, _, _ -> trySend(text) }
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}


