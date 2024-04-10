package com.elguille.purrfectpics.data.source

interface LocalDataSource<T> {
    suspend fun getLocalData(): T
}

interface RemoteDataSource<T> {
    suspend fun getRemoteData(): T
}