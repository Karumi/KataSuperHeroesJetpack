package com.karumi.jetpack.superheroes.ui.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.setImageBackground(path: String?) {
    if (path != null) {
        Picasso.get().load(path).fit().centerCrop().fit().into(this)
    }
}