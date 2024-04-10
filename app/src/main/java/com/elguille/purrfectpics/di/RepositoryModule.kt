package com.elguille.purrfectpics.di

import android.content.Context
import com.elguille.purrfectpics.BuildConfig
import com.elguille.purrfectpics.data.model.CatPicItem
import com.elguille.purrfectpics.data.source.LocalDataSource
import com.elguille.purrfectpics.data.source.RemoteDataSource
import com.elguille.purrfectpics.data.source.local.LocalAssetsJsonSource
import com.elguille.purrfectpics.data.source.remote.CatAaSApi
import com.elguille.purrfectpics.data.source.remote.CatAaSApiRemoteSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder =
            OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(15, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    @Singleton
    fun providesJson(): Json = Json {
            ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesRetrofit(json: Json, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.CATAAS_API_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
    }

    @Provides
    @Singleton
    fun providesCatAaSApi(retrofit: Retrofit): CatAaSApi =
        retrofit.create(CatAaSApi::class.java)

    @Provides
    @Singleton
    fun providesLocalCatPicRefsDataSource(@ApplicationContext context: Context, json: Json): LocalAssetsJsonSource{
        return LocalAssetsJsonSource(context, json)
    }

    @Provides
    @Singleton
    fun providesRemoteCatPicRefsDataSource(api: CatAaSApi): CatAaSApiRemoteSource {
        return CatAaSApiRemoteSource(api)
    }

    @Provides
    @Singleton
    fun providesLocalDataSource(local: LocalAssetsJsonSource): LocalDataSource<List<CatPicItem>> {
        return local
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(remote: CatAaSApiRemoteSource): RemoteDataSource<List<CatPicItem>> {
        return remote
    }

}