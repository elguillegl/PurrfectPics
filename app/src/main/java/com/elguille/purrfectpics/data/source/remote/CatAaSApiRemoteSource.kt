package com.elguille.purrfectpics.data.source.remote

import com.elguille.purrfectpics.data.model.CatPicItem
import com.elguille.purrfectpics.data.source.RemoteDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatAaSApiRemoteSource @Inject constructor(private val api: CatAaSApi): RemoteDataSource<List<CatPicItem>> {
    override suspend fun getRemoteData(): List<CatPicItem> {
        return coroutineScope {
            // Prepare the retrieval of 10 random cat pic descriptors
            (1..10).map {
                async {
                    api.getCatPic()
                }
                // Wait for the retrieval of all 10 random cat pic descriptor in parallel
            }.awaitAll()
        }
    }
}