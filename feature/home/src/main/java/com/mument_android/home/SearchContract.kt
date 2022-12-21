package com.mument_android.home

import com.mument_android.core.util.Event
import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.ViewState

class SearchContract {
    data class SearchViewState(
        override val hasError: Boolean = false,
        override val onNetwork: Boolean = false,

    ): ViewState

    sealed class SearchEvent: Event {
        data class OnClickMusic(val musicId: String): SearchEvent()
        object OnClickSearch: SearchEvent()
        object OnClickNotification: SearchEvent()
    }

    sealed class SearchSideEffect: SideEffect {
        object PopBackStack: SearchSideEffect()
        data class Toast(val message: String): SearchSideEffect()
        data class NavToMusicDetail(val musicId: String): SearchSideEffect()
        data class NavToMumentDetail(val musicId: String): SearchSideEffect()
    }
}