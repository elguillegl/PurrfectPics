package com.elguille.purrfectpics.di

import com.elguille.purrfectpics.BuildConfig
import com.elguille.purrfectpics.data.source.CatAASApi
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

@Module
@InstallIn(SingletonComponent::class)
object APIModule {
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder =
            OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(15, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.CATAAS_API_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
    }


    @Provides
    fun providesKnowledgeService(retrofit: Retrofit): CatAASApi =
        retrofit.create(CatAASApi::class.java)
}