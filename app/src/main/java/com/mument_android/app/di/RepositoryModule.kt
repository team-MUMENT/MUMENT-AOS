package com.mument_android.app.di

import com.mument_android.data.controller.*
import com.mument_android.data.datasource.detail.*
import com.mument_android.data.datasource.home.*
import com.mument_android.data.datasource.mypage.*
import com.mument_android.data.mapper.mypage.*
import com.mument_android.data.mapper.sign.*
import com.mument_android.data.repository.*
import com.mument_android.data.repository.mypage.*
import com.mument_android.domain.repository.app.LimitUserRepository
import com.mument_android.domain.repository.detail.*
import com.mument_android.domain.repository.home.HomeRepository
import com.mument_android.domain.repository.locker.LockerRepository
import com.mument_android.domain.repository.main.LikeMumentRepository
import com.mument_android.domain.repository.mypage.*
import com.mument_android.domain.repository.notify.NotifyRepository
import com.mument_android.domain.repository.record.RecordRepository
import com.mument_android.domain.repository.sign.SignRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMumentMainUseCase(likeMumentRepositoryImpl: LikeMumentRepositoryImpl): LikeMumentRepository

    @Binds
    @Singleton
    abstract fun bindMumentDetailUseCase(mumentDetailRepositoryImpl: MumentDetailRepositoryImpl): MumentDetailRepository

    @Binds
    @Singleton
    abstract fun bindLockerRepository(lockerRepositoryImpl: LockerRepositoryImpl): LockerRepository

    @Binds
    @Singleton
    abstract fun bindRecordRepository(recordRepositoryImpl: RecordRepositoryImpl): RecordRepository


    @Binds
    @Singleton
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun bindNotifyRepository(notifyRepositoryImpl: NotifyRepositoryImpl): NotifyRepository

    @Binds
    @Singleton
    abstract fun bindMusicDetailRepository(musicDetailRepositoryImpl: MusicDetailRepositoryImpl): MusicDetailRepository

    @Binds
    @Singleton
    abstract fun bindMumentListRepository(mumentListRepositoryImpl: MumentListRepositoryImpl): MumentListRepository

    @Binds
    @Singleton
    abstract fun bindSignRepository(signRepositoryImpl: SignRepositoryImpl): SignRepository

    @Binds
    @Singleton
    abstract fun bindBlockUserRepository(blockUserRepositoryImpl: BlockUserRepositoryImpl): BlockUserRepository

    @Binds
    @Singleton
    abstract fun bindBlockUserListRepository(blockUserListRepositoryImpl: BlockUserListRepositoryImpl): BlockUserListRepository

    @Binds
    @Singleton
    abstract fun bindNoticeListRepository(noticeListRepositoryImpl: NoticeListRepositoryImpl): NoticeListRepository

    @Binds
    @Singleton
    abstract fun bindUserInfoRepository(userInfoRepositoryImpl: UserInfoRepositoryImpl): UserInfoRepository


    @Binds
    @Singleton
    abstract fun bindLimitUserRepository(limitUserRepositoryImpl: LimitUserRepositoryImpl): LimitUserRepository

    @Binds
    @Singleton
    abstract fun bindUsersLikeMumentRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository

    @Binds
    @Singleton
    abstract fun bindUnregisterRepository(unregisterRepositoryImpl: UnregisterRepositoryImpl): UnregisterRepository

    @Binds
    @Singleton
    abstract fun bindUnregisterReasonRepository(unregisterReasonRepositoryImpl: UnregisterReasonRepositoryImpl): UnregisterReasonRepository

    @Binds
    @Singleton
    abstract fun bindLogOutRepository(logOutRepositoryImpl: LogOutRepositoryImpl): LogOutRepository
}
