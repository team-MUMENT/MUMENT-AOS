package com.mument_android.home.main

import com.mument_android.core.util.Event
import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.ViewState
import com.mument_android.domain.entity.home.AgainMumentEntity
import com.mument_android.domain.entity.home.BannerEntity
import com.mument_android.domain.entity.home.RandomMumentEntity
import com.mument_android.domain.entity.home.TodayMument
import com.mument_android.domain.entity.music.MusicInfoEntity

class HomeContract {
    data class HomeViewState(
        override val hasError: Boolean = false,
        override val onNetwork: Boolean = false,
        val bannerEntity: List<BannerEntity>? = null,
        val todayMumentEntity: TodayMument? = null,
        val heardMumentEntity: List<AgainMumentEntity>? = null,
        val emotionMumentEntity: RandomMumentEntity? = null,
        val notificationStatus: Boolean = false,
        val profileStatus: Boolean = false,
        val navStart: String = ""
    ) : ViewState

    sealed class HomeEvent : Event {
        data class OnClickBanner(val musicInfo: MusicInfoEntity) : HomeEvent()
        data class OnClickTodayMument(val mument: String, val musicInfo: MusicInfoEntity) :
            HomeEvent()//하나로만 해도 되는데 이벤트 수집 할 수도 있으니 분기처리 하였음.

        data class OnClickHeardMument(val mument: String, val musicInfo: MusicInfoEntity) :
            HomeEvent()

        data class OnClickRandomMument(val mument: String, val musicInfo: MusicInfoEntity) :
            HomeEvent()

        data class ReEntryToSearchView(val musicInfo: MusicInfoEntity, val startNav: String) : HomeEvent()
        object ReEntryToNotificationView : HomeEvent()

        object OnClickSearch : HomeEvent()
        object OnClickNotification : HomeEvent()
        data class NotificationToMumentDetail(val mument: String, val musicInfo: MusicInfoEntity): HomeEvent()
    }

    sealed class HomeSideEffect : SideEffect {
        object GoToSearchActivity : HomeSideEffect()
        object GoToNotification : HomeSideEffect()
        data class Toast(val message: String) : HomeSideEffect()
        data class NavToMusicDetail(val musicInfo: MusicInfoEntity, val startNav: String) : HomeSideEffect()
        data class NavToMumentDetail(val mumentId: String, val musicInfo: MusicInfoEntity, val startNav: String) : HomeSideEffect()
    }
}