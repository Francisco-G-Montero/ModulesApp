package com.frommetoyou.modulesapp2.presentation.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.frommetoyou.modulesapp2.presentation.ui.fragment.IMG_IORI_URL

fun ImageView.loadUrlImage(urlImage: String){
    Glide.with(context)
        .load(urlImage)
        .into(this)
}