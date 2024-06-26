package com.elguille.purrfectpics.data.repository

import android.util.Log
import com.elguille.purrfectpics.data.model.CatPic
import com.elguille.purrfectpics.data.source.remote.CatAaSApi
import com.elguille.purrfectpics.domain.DataError
import com.elguille.purrfectpics.domain.Resource
import com.elguille.purrfectpics.extensions.toDataError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatPicRepository @Inject constructor(
    private val remoteSource: CatAaSApi
) {
    suspend fun getCatPics(): Resource<List<CatPic>, DataError> {
        try {
            val catPics = remoteSource.getCatPics(limit = NUM_CAT_PICS)
            return Resource.Success(catPics)
        } catch (e: Exception) {
            Log.e("CatPicRepository", "getCatPics: ", e)
            return Resource.Error(e.toDataError())
        }
    }

    suspend fun getCatPic(id: String): Resource<CatPic, DataError> {
        try {
            val catPic = remoteSource.getCatPic(id = id)
            return Resource.Success(catPic)
        } catch (e: Exception) {
            Log.e("CatPicRepository", "getCatPic: ", e)
            return Resource.Error(e.toDataError())
        }
    }

    companion object {
        const val NUM_CAT_PICS = 10
    }

}