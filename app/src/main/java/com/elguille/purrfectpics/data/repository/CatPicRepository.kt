package com.elguille.purrfectpics.data.repository

import com.elguille.purrfectpics.data.model.CatPic
import com.elguille.purrfectpics.data.source.remote.CatAaSApi
import com.elguille.purrfectpics.domain.DataError
import com.elguille.purrfectpics.domain.Resource
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
            return Resource.Error(DataError.Network)
        }
    }

    suspend fun getCatPic(id: String): Resource<CatPic, DataError> {
        try {
            val catPic = remoteSource.getCatPic(id = id)
            return Resource.Success(catPic)
        } catch (e: Exception) {
            return Resource.Error(DataError.Network)
        }
    }

    companion object {
        const val NUM_CAT_PICS = 10
    }

}