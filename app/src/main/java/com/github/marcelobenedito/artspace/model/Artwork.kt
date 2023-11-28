package com.github.marcelobenedito.artspace.model

import androidx.annotation.DrawableRes

data class Artwork(
    @DrawableRes val image: Int,
    val title: String,
    val artist: String,
    val year: Int
)