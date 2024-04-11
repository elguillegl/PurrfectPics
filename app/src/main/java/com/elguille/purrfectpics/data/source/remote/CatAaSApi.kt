package com.elguille.purrfectpics.data.source.remote

import androidx.annotation.IntDef
import androidx.annotation.StringDef
import com.elguille.purrfectpics.data.model.CatPic
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatAaSApi {
    @GET("v1/images/search?has_breeds=1&order=RAND")
    suspend fun getCatPics(
        @Query("limit") limit: Int
    ): List<CatPic>

    @GET("v1/images/{id}")
    suspend fun getCatPic(@Path("id") id: String): CatPic
}