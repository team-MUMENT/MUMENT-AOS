package com.mument_android.app.presentation.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.usecase.home.CRURecentSearchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val cruRecentSearchListUseCase: CRURecentSearchListUseCase) :
    ViewModel() {
    val searchList = MutableStateFlow<ApiResult<List<RecentSearchData>>?>(null)

    val searchContent = MutableStateFlow<RecentSearchData?>(null)
    val searchResultList = MutableStateFlow<List<RecentSearchData>?>(listOf())
    fun selectContent(data: RecentSearchData) {
        searchContent.value = data
    }

    init {
        setRecentData()
    }

    fun setRecentData() {

        viewModelScope.launch {
            cruRecentSearchListUseCase.getAllRecentSearchList().onStart {
                searchList.value = ApiResult.Loading(null)
            }.catch {
                searchList.value = ApiResult.Failure(null)
            }.collect {
                if (it.isNotEmpty()) {
                    searchList.value = ApiResult.Success(it)
                }
            }
        }
    }

    fun searchMusic(keyword: String) {

        viewModelScope.launch {
            cruRecentSearchListUseCase.insertOrUpdateRecentSearchItem(
                RecentSearchData(
                    Random().nextInt(
                        50
                    ).toString(),
                    "이민호",
                    "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                    keyword,
                    Date(System.currentTimeMillis())
                )
            ).let {
                setRecentData()
            }
        }
        /*searchResultList.value = listOf(
            RecentSearchData(
                "1",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "2",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "3",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "4",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "5",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "6",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "7",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "8",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "9",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "10",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "11",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "12",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "13",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "14",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "15",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            ),
            RecentSearchData(
                "16",
                "덩",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "새소년",
                Date(System.currentTimeMillis())
            )
        )*/
    }

    fun insertOrUpdateRecentItem(data: RecentSearchData) {

    }

    fun deleteRecentList(data: RecentSearchData) {
        /*val current = searchList.value?.toMutableList()
        current?.remove(data)
        searchList.value = current*/
    }

    fun allListDelete() {
        /*searchList.value = listOf()*/
    }
}