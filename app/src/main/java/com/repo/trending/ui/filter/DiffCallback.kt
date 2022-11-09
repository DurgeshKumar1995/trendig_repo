package com.repo.trending.ui.filter

import androidx.recyclerview.widget.DiffUtil

open class DiffCallback<T> (private val oldList:List<T>,private val newList:List<T>):DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]==newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]==newList[newItemPosition]
    }
}

fun <T> updateList(newList: List<T>,oldList: List<T>): DiffUtil.DiffResult{
    val diffCallback = DiffCallback(oldList,newList)
    return DiffUtil.calculateDiff(diffCallback)
}