package com.mument_android.app.di

import android.content.Context
import com.mument_android.BuildConfig
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.network.RefreshTokenApiService
import com.mument_android.core_dependent.network.TokenDataSource
import com.mument_android.data.network.AuthInterceptor
import com.mument_android.data.network.app.AppApiService
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.data.network.detail.HistoryService
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.network.home.NotifyService
import com.mument_android.data.network.locker.LockerApiService
import com.mument_android.data.network.main.MainApiService
import com.mument_android.data.network.mypage.MyPageApiService
import com.mument_android.data.network.record.RecordApiService
import com.mument_android.data.network.sign.SignApiService
import com.mument_android.data.util.ErrorHandlerImpl
import com.mument_android.domain.util.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    inline fun <reified T> Retrofit.create(): T = create(T::class.java)

    @Provides
    @Singleton
    fun provideErrorHandler(): ErrorHandler = ErrorHandlerImpl()


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        @ApplicationContext context: Context,
        dataStoreManager: DataStoreManager,
        tokenDataSource: TokenDataSource
    ): AuthInterceptor =
        AuthInterceptor(context, dataStoreManager, tokenDataSource)

    /** Header에 Token값을 포함하는 OkHttpClient, Retrofit **/

    @Provides
    @Singleton
    @AuthOkHttpClient
    fun provideAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
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


    /** Api Interface **/

    @Provides
    @Singleton
    fun provideDetailApiService(@AuthRetrofit retrofit: Retrofit): DetailApiService =
        retrofit.create(DetailApiService::class.java)

    @Provides
    @Singleton
    fun provideHistoryService(@AuthRetrofit retrofit: Retrofit): HistoryService =
        retrofit.create(HistoryService::class.java)

    @Provides
    @Singleton
    fun provideMainApiService(@AuthRetrofit retrofit: Retrofit): MainApiService =
        retrofit.create(MainApiService::class.java)

    @Provides
    @Singleton
    fun provideRecordApiService(@AuthRetrofit retrofit: Retrofit): RecordApiService =
        retrofit.create(RecordApiService::class.java)

    @Provides
    @Singleton
    fun provideLockerNetwork(@AuthRetrofit retrofit: Retrofit): LockerApiService =
        retrofit.create(LockerApiService::class.java)

    @Provides
    @Singleton
    fun provideNotifyNetwork(@AuthRetrofit retrofit: Retrofit): NotifyService =
        retrofit.create(NotifyService::class.java)

    @Provides
    @Singleton
    fun providehomeNetwork(@AuthRetrofit retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun provideSignNetwork(@AuthRetrofit retrofit: Retrofit): SignApiService =
        retrofit.create(SignApiService::class.java)

    @Provides
    @Singleton
    fun provideMyPageNetwork(@AuthRetrofit retrofit: Retrofit): MyPageApiService = retrofit.create(MyPageApiService::class.java)

    @Provides
    @Singleton
    fun provideAppNetwork(@AuthRetrofit retrofit: Retrofit): AppApiService = retrofit.create(AppApiService::class.java)

    @Provides
    @Singleton
    fun provideRefreshTokenApiService(@UnAuthRetrofit retrofit: Retrofit): RefreshTokenApiService =
        retrofit.create()

    private fun Request.Builder.addHeaders(token: String) =
        this.apply { header("Authorization", "Bearer $token") }

    private const val BEARER = "Bearer"
}