package com.example.bankapp.extensions

import androidx.appcompat.widget.AppCompatImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.bankapp.R

fun AppCompatImageView.loadSvg(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
        .build()


    val request = ImageRequest.Builder(this.context)
        .data(url)
        .placeholder(R.drawable.ic_baseline_monetization_on_24)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}