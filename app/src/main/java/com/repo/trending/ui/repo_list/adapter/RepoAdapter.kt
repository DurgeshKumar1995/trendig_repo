package com.repo.trending.ui.repo_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.repo.trending.databinding.ItemRepoBinding
import com.repo.trending.model.Repo

class RepoAdapter(private val clicked: (Repo?) -> Unit): PagingDataAdapter<Repo, RepoAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }

    inner class NoteViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Repo?) {
            binding.repo = data

            binding.let {
                it.root.setOnClickListener {
                    clicked.invoke(data)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
