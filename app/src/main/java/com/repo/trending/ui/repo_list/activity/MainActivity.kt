package com.repo.trending.ui.repo_list.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.repo.trending.databinding.ActivityMainBinding
import com.repo.trending.model.Repo
import com.repo.trending.ui.repo_list.adapter.RepoAdapter
import com.repo.trending.ui.repo_list.adapter.LoadStateAdapter
import com.repo.trending.ui.repo_list.view_model.MainViewModel
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
            footer = LoadStateAdapter(),
            header = LoadStateAdapter()
        )

        lifecycleScope.launch {

            viewModel.refreshPoint.observe(this@MainActivity){
                if (it==true)
                    adapter.refresh()
            }

            viewModel.getRepos().collectLatest {
                adapter.submitData(it)
                binding.apply {
                    recyclerView.isVisible = true
                  //  progress.isVisible = false
                }
            }
        }



        adapter.addLoadStateListener { loadState ->

//            if (loadState.append.endOfPaginationReached) {
//                handleVisibility(adapter.itemCount < 1)
//            }
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
        }

    private fun handleVisibility(isEmpty:Boolean){
        binding.recyclerView.isVisible = !isEmpty
        binding.txtEmpty.isVisible = isEmpty

    }
}