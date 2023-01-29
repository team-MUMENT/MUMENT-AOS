package com.mument_android.home.search

import com.mument_android.core.util.Event
import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.ViewState
import com.mument_android.domain.entity.home.RecentSearchData

class SearchContract {
    data class SearchViewState(
        override val hasError: Boolean = false,
        override val onNetwork: Boolean = false,
        val recentSearchList: List<RecentSearchData>? = null,
        val searchResultList: List<RecentSearchData>? = null
    ) : ViewState

    sealed class SearchEvent : Event {
        data class OnClickMusic(val musicId: String) : SearchEvent() //음악 선택
        data class OnClickMusicDelete(val recentSearchData: RecentSearchData) :
            SearchEvent() //음악 삭제

        data class OnSearchMusic(val searchStr: String) : SearchEvent()  //음악 검색
        data class OnSearchOptionChange(val option: Boolean) : SearchEvent()  //최근 검색 or 검색 리스트 전환
        object OnClickSearchClear : SearchEvent()  // 검색어 Clear
        object OnClickAllDelete : SearchEvent()    // 최근 검색 기록 전체 삭제 클릭
        object ApplyAllDelete : SearchEvent()    // 전체 삭제 확인
    }

    sealed class SearchSideEffect : SideEffect {
        object PopBackStack : SearchSideEffect()   // 뒤로가기
        object OpenAllDeleteDialog : SearchSideEffect()   // 전체 삭제 여부 다이어로그
        object AllDelete : SearchSideEffect()   // 전체 삭제
        data class Toast(val message: String) : SearchSideEffect()     //토스트
        data class Search(val searchStr: String) : SearchSideEffect()  //검색 API Call
        data class NavToMusicDetail(val musicId: String) : SearchSideEffect() //Go to Music Detail
    }
}