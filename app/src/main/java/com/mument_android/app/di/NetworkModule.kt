package com.mument_android.app.di

import com.mument_android.BuildConfig
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.network.locker.LockerApiService
import com.mument_android.data.network.main.MainApiService
import com.mument_android.data.network.record.RecordApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        with(chain) {
            val request = request()
                .newBuilder()
                .addHeader(
                    "Authorization",
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzAsInByb2ZpbGVJZCI6IuyViOuTnO2FjOyKpO2KuOyaqSIsImltYWdlIjpudWxsLCJpYXQiOjE2NzMxMjYzNzgsImV4cCI6MTY3NTcxODM3OCwiaXNzIjoiTXVtZW50In0.PG_Cubw4nv9USBiKKMVaAxS-Ggl6ByqOKusmyK4tp18"
                )
                .addHeader("Content-Type", "application/json")
                .addHeader(
                    "refreshToken",
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzAsInByb2ZpbGVJZCI6IuyViOuTnO2FjOyKpO2KuOyaqSIsImltYWdlIjpudWxsLCJpYXQiOjE2NzMxMjYzNzgsIm5iZiI6MTY3NTcxODM3OCwiZXhwIjoxNjc4MzEwMzc4LCJpc3MiOiJNdW1lbnQifQ.hx2qgOWQTnlaLpC8AclA3PO1W89VgVpvSCrIcq171zU"
                )
                .build()
            proceed(request)

        }
        //val request = chain.request().newBuilder().addHeaders(BuildConfig.USER_ID).build()
        //chain.proceed(request)
    }

    /** Header에 Token값을 포함하는 OkHttpClient, Retrofit **/

    @Provides
    @Singleton
    @AuthOkHttpClient
    fun provideAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

    @Singleton
    @Provides
    @AuthRetrofit
    fun provideAuthRetrofit(@AuthOkHttpClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /** Header값 없이 요청을 보내는 OkHttpClient, Retrofit **/

    @Provides
    @Singleton
    @UnAuthOkHttpClient
    fun provideUnAuthOkHttpClientBuilder(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(provideAuthInterceptor())
            .build()

    @Provides
    @Singleton
    @UnAuthRetrofit
    fun provideUnAuthRetrofit(@UnAuthOkHttpClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideDetailApiService(@UnAuthRetrofit retrofit: Retrofit): DetailApiService =
        retrofit.create(DetailApiService::class.java)

    @Provides
    @Singleton
    fun provideMainApiService(@UnAuthRetrofit retrofit: Retrofit): MainApiService =
        retrofit.create(MainApiService::class.java)

    @Provides
    @Singleton
    fun provideRecordApiService(@UnAuthRetrofit retrofit: Retrofit): RecordApiService =
        retrofit.create(RecordApiService::class.java)

    @Provides
    @Singleton
    fun provideLockerNetwork(@UnAuthRetrofit retrofit: Retrofit): LockerApiService =
        retrofit.create(LockerApiService::class.java)

    @Provides
    @Singleton
    fun providehomeNetwork(@UnAuthRetrofit retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)


    private fun Request.Builder.addHeaders(token: String) = this.apply { header(BEARER, token) }
    private const val BEARER = "Bearer"
}