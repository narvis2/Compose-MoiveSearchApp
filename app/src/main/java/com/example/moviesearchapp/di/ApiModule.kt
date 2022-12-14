package com.example.moviesearchapp.di

import com.example.data.api.NaverApiService
import com.example.data.repository.datasource.RemoteDataSource
import com.example.data.repository.datasourceimpl.RemoteDataSourceImpl
import com.example.moviesearchapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(naverApiService: NaverApiService): RemoteDataSource =
        RemoteDataSourceImpl(naverApiService)

    @Singleton
    @Provides
    fun provideNaverApiService(
        retrofit: Retrofit
    ): NaverApiService = retrofit.create(NaverApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        converter: GsonConverterFactory
    ): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.API_HOST).client(client)
        .addConverterFactory(converter).build()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        logging: HttpLoggingInterceptor,
        interceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(logging)
        }

        addInterceptor(interceptor)
    }.build()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder().apply {
                addHeader("X-Naver-Client-Id", BuildConfig.NAVE_CLIENT_ID)
                addHeader("X-Naver-Client-Secret", BuildConfig.NAVE_CLIENT_SECRET)
            }.build()
        )
    }
}