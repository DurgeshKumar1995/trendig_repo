package com.repo.trending.ui.repo_list.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.repo.trending.R
import com.repo.trending.databinding.ActivityMainBinding
import com.repo.trending.model.Repo
import com.repo.trending.ui.filter.view.FilterActivity
import com.repo.trending.ui.repo_list.adapter.RepoAdapter
import com.repo.trending.ui.repo_list.adapter.LoadStateAdapter
import com.repo.trending.ui.repo_list.view_model.MainViewModel
import com.repo.trending.utils.NetworkUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {


    private val viewModel by viewModel<MainViewModel>()

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { RepoAdapter { actionOnItemClick(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)

        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            footer = LoadStateAdapter(adapter),
            header = LoadStateAdapter(adapter)
        )

        lifecycleScope.launch {

            viewModel.refreshPoint.observe(this@MainActivity){
                if (it==true)
                    adapter.refresh()
            }

            viewModel.getRepos().collectLatest {
                adapter.submitData(it)
            }

            lifecycleScope.launch {
                adapter.loadStateFlow.collectLatest { loadStates ->
                    binding.progress.isVisible = loadStates.refresh is LoadState.Loading
                    binding.retryButton.isVisible = loadStates.refresh !is LoadState.Loading
                    binding.errorMsg.isVisible = loadStates.refresh is LoadState.Error

                }
            }

        }

        binding.btnSearch.setOnClickListener {
            it.isEnabled = false
            val intent = Intent(applicationContext,FilterActivity::class.java)
            startForResult.launch(intent)
        }

        if (!NetworkUtil.isOnline(this)){
            Toast.makeText(baseContext, getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show()
        }


    }

    private fun actionOnItemClick(repo: Repo? = null) {
        if (repo!=null) {
            repo.isChecked = true
            viewModel.updateRepo(repo)
        }
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            if (result.resultCode == Activity.RESULT_OK) {
                adapter.refresh()
            }
            binding.btnSearch.isEnabled = true
        }


}