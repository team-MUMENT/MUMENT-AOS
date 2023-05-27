package com.mument_android.app.di

import com.mument_android.domain.repository.detail.*
import com.mument_android.domain.repository.mypage.*
import com.mument_android.domain.usecase.app.LimitUserUseCase
import com.mument_android.domain.usecase.app.LimitUserUseCaseImpl
import com.mument_android.domain.usecase.detail.*
import com.mument_android.domain.usecase.home.*
import com.mument_android.domain.usecase.locker.FetchMyLikeListUseCase
import com.mument_android.domain.usecase.locker.FetchMyLikeListUseCaseImpl
import com.mument_android.domain.usecase.locker.FetchMyMumentListUseCase
import com.mument_android.domain.usecase.locker.FetchMyMumentListUseCaseImpl
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCaseImpl
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCaseImpl
import com.mument_android.domain.usecase.mypage.*
import com.mument_android.domain.usecase.notify.*
import com.mument_android.domain.usecase.record.*
import com.mument_android.domain.usecase.sign.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    @ViewModelScoped
    abstract fun bindFetchMumentDetailContentUseCase(fetchMumentDetailContentUseCaseImpl: FetchMumentDetailContentUseCaseImpl): FetchMumentDetailContentUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindIsFirstRecordMumentUseCase(isFirstRecordMumentUseCaseImpl: IsFirstRecordMumentUseCaseImpl): IsFirstRecordMumentUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindLikeMumentUseCase(likeMumentUseCaseImpl: LikeMumentUseCaseImpl): LikeMumentUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFetchMyMumentListUseCase(fetchMyMumentListUseCaseImpl: FetchMyMumentListUseCaseImpl): FetchMyMumentListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFetchLockerLikeListUseCase(fetchMyLikeListUseCaseImpl: FetchMyLikeListUseCaseImpl): FetchMyLikeListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindCancelLikeMumentUseCase(cancelLikeMumentUseCaseImpl: CancelLikeMumentUseCaseImpl): CancelLikeMumentUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindCRURecentSearchListUseCase(cruRecentSearchListUseCaseImpl: CRURecentSearchListUseCaseImpl): CRURecentSearchListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindDeleteRecentSearchListUseCase(deleteRecentSearchListUseCaseImpl: DeleteRecentSearchListUseCaseImpl): DeleteRecentSearchListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSearchMusicUseCase(searchMusicUseCaseImpl: SearchMusicUseCaseImpl): SearchMusicUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetMumentHistoryUseCase(getMumentHistoryUseCaseImpl: GetMumentHistoryUseCaseImpl): GetMumentHistoryUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFetchNotifyListDeleteUseCase(fetchNotifyListDeleteUseCaseImpl: FetchNotifyListDeleteUseCaseImpl): FetchNotifyListDeleteUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFetchNotifyListsReadUseCase(fetchNotifyListsReadUseCaseImpl: FetchNotifyListsReadUseCaseImpl): FetchNotifyListsReadUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFetchNotifyListUseCase(fetchNotifyListUseCaseImpl: FetchNotifyListUseCaseImpl): FetchNotifyListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindRecordMumentUseCase(recordMumentUseCaseImpl: RecordMumentUseCaseImpl): RecordMumentUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindRecordModifyMumentUseCase(recordModifyMumentUseCaseImpl: RecordModifyMumentUseCaseImpl): RecordModifyMumentUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindWhenHomeEnterUseCase(whenHomeEnterUseCaseImpl: WhenHomeEnterUseCaseImpl): WhenHomeEnterUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindBeforeWhenHomeEnterUseCase(beforeWhenHomeEnterUseCaseImpl: BeforeWhenHomeEnterUseCaseImpl): BeforeWhenHomeEnterUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFetchMumentListUseCase(fetchMumentListUseCaseImpl: FetchMumentListUseCaseImpl): FetchMumentListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFetchMusicDetailUseCase(fetchMusicDetailUseCaseImpl: FetchMusicDetailUseCaseImpl): FetchMusicDetailUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindDeleteMumentUseCase(deleteMumentUseCaseImpl: DeleteMumentUseCaseImpl): DeleteMumentUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSignDulCheck(signDulCheckUseCaseImpl: SignDulCheckUseCaseImpl): SignDulCheckUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindBlockUserUseCase(blockUserUseCaseImpl: BlockUserUseCaseImpl): BlockUserUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSignPutProfileUseCase(signPutProfileUseCaseImpl: SignPutProfileUseCaseImpl): SignPutProfileUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFetchBlockUserListUseCase(fetchBlockUserUseCaseImpl: FetchBlockUserUseCaseImpl): FetchBlockUserUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindDeleteBlockUserUseCase(deleteBlockUserUseCaseImpl: DeleteBlockUserUseCaseImpl): DeleteBlockUserUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFetchNoticeListUseCase(fetchNoticeListUseCaseImpl: FetchNoticeListUseCaseImpl): FetchNoticeListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFetchNoticeDetailUseCase(fetchNoticeDetailUseCaseImpl: FetchNoticeDetailUseCaseImpl): FetchNoticeDetailUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindKaKaoLoginUseCase(signKaKaoUseCaseImpl: SignKaKaoUseCaseImpl): SignKaKaoUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindUserInfoUseCase(userInfoUseCaseImpl: UserInfoUseCaseImpl): UserInfoUseCase


    @Binds
    @ViewModelScoped
    abstract fun bindGetWebViewUseCase(getWebViewUseCaseImpl: GetWebViewUseCaseImpl): GetWebViewUseCase


    @Binds
    @ViewModelScoped
    abstract fun bindLimitUserUseCase(limitUserUseCaseImpl: LimitUserUseCaseImpl): LimitUserUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindNewTokenUseCase(newTokenUseCaseImpl: NewTokenUseCaseImpl): NewTokenUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFetchUsersLikeMumentUseCase(fetchUsersLikeMumentUseCaseImpl: FetchUsersLikeMumentUseCaseImpl): FetchUsersLikeMumentUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFetchUnregisterInfoUseCase(fetchUnregisterInfoUseCaseImpl: FetchUnregisterInfoUseCaseImpl): FetchUnregisterInfoUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindPostUnregisterReasonUseCase(postUnregisterReasonUseCaseImpl: PostUnregisterReasonUseCaseImpl): PostUnregisterReasonUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindReportMumentUseCase(reportMumentUseCaseImpl: ReportMumentUseCaseImpl): ReportMumentUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindLogOutUseCase(logOutUseCaseImpl: LogOutUseCaseImpl): LogOutUseCase
}