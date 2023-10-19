package com.example.routee_commerce.utils


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.routee_commerce.R
import com.squareup.picasso.Picasso

class BindingAdapters {
    companion object {
        @BindingAdapter("app:url")
        @JvmStatic
        fun bindImage(imageView: ImageView, url: String?) {
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_category_placeholder)
                .transform(TransformCircular())
                .into(imageView)
        }
    }
}