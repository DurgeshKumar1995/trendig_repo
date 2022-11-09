package com.repo.trending.ui.repo_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.repo.trending.databinding.ItemRepoBinding
import com.repo.trending.ui.common_model.Repo

class RepoAdapter(private val clicked: (Repo?) -> Unit): PagingDataAdapter<Repo, RepoAdapter.RepoViewHolder>(
    DIFF_CALLBACK
) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }

    inner class RepoViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Repo, position: Int) {
            binding.repo = data

            binding.let {
                it.root.setOnClickListener {
                    data.isChecked =true
                    clicked.invoke(data)
                    notifyItemChanged(position)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = getItem(position)?:return
        holder.bind(item,position)
    }
}
