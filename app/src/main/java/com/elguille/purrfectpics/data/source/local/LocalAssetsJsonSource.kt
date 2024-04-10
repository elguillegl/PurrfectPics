package com.elguille.purrfectpics.data.source.local

import android.content.Context
import com.elguille.purrfectpics.data.model.CatPicItem
import com.elguille.purrfectpics.data.source.LocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalAssetsJsonSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json): LocalDataSource<List<CatPicItem>> {
    override suspend fun getLocalData(): List<CatPicItem> = withContext(Dispatchers.IO) {
        val jsonString = context.assets.open(ASSET_FILE_NAME).bufferedReader().use { it.readText() }

        json.decodeFromString(jsonString)
    }

    companion object {
        private const val ASSET_FILE_NAME = "json/cat_references.json"
    }
}