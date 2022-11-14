package com.mument_android.app.di

import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.network.locker.LockerApiService
import com.mument_android.data.network.main.MainApiService
import com.mument_android.data.network.record.RecordApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val loggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideUnAuthOkHttpClientBuilder(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideUnAuthRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://15.164.129.17:8000")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideDetailApiService(retrofit: Retrofit): DetailApiService =
        retrofit.create(DetailApiService::class.java)

    @Provides
    @Singleton
    fun provideMainApiService(retrofit: Retrofit): MainApiService =
        retrofit.create(MainApiService::class.java)

    @Provides
    @Singleton
    fun provideRecordApiService(retrofit: Retrofit): RecordApiService = retrofit.create(RecordApiService::class.java)

    @Provides
    @Singleton
    fun provideLockerNetwork(retrofit: Retrofit): LockerApiService =
        retrofit.create(LockerApiService::class.java)

    @Provides
    @Singleton
    fun providehomeNetwork(retrofit: Retrofit): HomeService = retrofit.create(HomeService::class.java)

}