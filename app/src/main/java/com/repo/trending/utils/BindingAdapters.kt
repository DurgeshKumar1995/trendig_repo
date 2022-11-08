package com.repo.trending.utils

import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.repo.trending.R
import com.repo.trending.model.Repo


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(view: AppCompatImageView,imageUrl:String?){
        if (imageUrl.isNullOrEmpty()){
            view.isVisible = false
            return
        }
        view.isVisible = true
        view.load(imageUrl){
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
        }
    }




}