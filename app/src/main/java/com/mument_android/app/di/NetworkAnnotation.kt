package com.mument_android.app.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttpClient()

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnAuthOkHttpClient()

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit()

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnAuthRetrofit()
