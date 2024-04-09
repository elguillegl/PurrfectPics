package com.elguille.purrfectpics.extensions

import com.elguille.purrfectpics.BuildConfig
import com.elguille.purrfectpics.data.model.CatPicItem

fun CatPicItem.pictureUrl(): String {
    val urlString = "${BuildConfig.CATAAS_API_URL}cat/${this.id}"

    println(urlString)

    return urlString
}