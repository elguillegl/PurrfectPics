package com.elguille.purrfectpics.di

import com.elguille.purrfectpics.BuildConfig
import com.elguille.purrfectpics.data.source.remote.CatAaSApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}