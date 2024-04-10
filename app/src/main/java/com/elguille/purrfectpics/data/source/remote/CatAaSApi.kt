package com.elguille.purrfectpics.data.source.remote

import com.elguille.purrfectpics.data.model.CatPicItem
import retrofit2.http.GET

interface CatAaSApi {
    @GET("cat?json=true")
    suspend fun getCatPic(): CatPicItem
}