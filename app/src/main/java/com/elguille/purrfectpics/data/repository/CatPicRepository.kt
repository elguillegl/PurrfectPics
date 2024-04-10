package com.elguille.purrfectpics.data.repository

import android.util.Log
import com.elguille.purrfectpics.data.model.CatPicItem
import com.elguille.purrfectpics.data.source.LocalDataSource
import com.elguille.purrfectpics.data.source.RemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatPicRepository @Inject constructor(
    private val remoteSource: RemoteDataSource<List<CatPicItem>>,
    private val localSource: LocalDataSource<List<CatPicItem>>
) {
    suspend fun getCatPics(): List<CatPicItem> {
        return try {
            remoteSource.getRemoteData()
        } catch (e: Exception) {
            Log.e("CatPicRepository", "Error retrieving cat pics from remote: ", e)
            Log.i("CatPicRepository", "Defaulting to cat pics from local")
            localSource.getLocalData()
        }
    }
}