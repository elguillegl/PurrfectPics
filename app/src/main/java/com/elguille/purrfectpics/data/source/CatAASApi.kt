package com.elguille.purrfectpics.data.source

import com.elguille.purrfectpics.data.model.CatPicItem
import retrofit2.http.GET

interface CatAASApi {
    @GET("cat?json=true")
    suspend fun getCatPic(): CatPicItem
}