package com.karumi.jetpack.superheroes.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: String?) {
    if (url != null) {
        Picasso.get().load(url).fit().centerCrop().fit().into(view)
    }
}