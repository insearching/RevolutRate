package com.insearching.revolutrate.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.insearching.revolutrate.R
import com.squareup.picasso.Picasso


@BindingAdapter("bind:src")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (imageUrl == null || imageUrl.isEmpty())
        return
    Picasso.get()
        .load(imageUrl)
        .transform(CircleTransformation())
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(view)
}