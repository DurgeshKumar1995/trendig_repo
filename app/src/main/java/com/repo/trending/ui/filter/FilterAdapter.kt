package com.repo.trending.ui.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.repo.trending.databinding.ItemRepoBinding
import com.repo.trending.ui.common_model.Repo

class FilterAdapter(private val clicked: (Repo?) -> Unit):RecyclerView.Adapter<FilterAdapter.RepoViewHolder>() {

    private val mainList = ArrayList<Repo>()

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
        val item = mainList[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int =mainList.size

    fun setList(list: List<Repo>?){
        val tempList  = list?: emptyList()
        updateList(tempList,mainList).dispatchUpdatesTo(this)
        mainList.run {
            clear()
            addAll(tempList)
        }
    }
}