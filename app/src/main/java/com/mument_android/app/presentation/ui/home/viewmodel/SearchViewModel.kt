package com.mument_android.app.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.usecase.home.CRURecentSearchListUseCase
import com.mument_android.app.domain.usecase.home.DeleteRecentSearchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val cruRecentSearchListUseCase: CRURecentSearchListUseCase,
    private val deleteRecentSearchListUseCase: DeleteRecentSearchListUseCase
) :
    ViewModel() {
    val searchList = MutableStateFlow<ApiResult<List<RecentSearchData>>?>(null)

    val searchContent = MutableStateFlow<RecentSearchData?>(null)
    val searchResultList = MutableStateFlow<List<RecentSearchData>?>(listOf())
    fun selectContent(data: RecentSearchData) {
        searchContent.value = data
    }

    init {
        setRecentData(viewModelScope)
    }

    fun setRecentData(scope: CoroutineScope) {
        scope.launch {
            cruRecentSearchListUseCase.getAllRecentSearchList().onStart {
                searchList.value = ApiResult.Loading(null)
            }.catch {
                searchList.value = ApiResult.Failure(null)
            }.collect {
                Timber.d("Emit $it")
                searchList.value = ApiResult.Success(it)
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
                setRecentData(this)
            }
        }
    }

    fun insertOrUpdateRecentItem(data: RecentSearchData) {
        viewModelScope.launch(Dispatchers.IO) {
            cruRecentSearchListUseCase.insertOrUpdateRecentSearchItem(data)
        }
    }

    fun deleteRecentList(data: RecentSearchData) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteRecentSearchListUseCase.deleteRecentSearchItem(data).let {
                setRecentData(this)
            }
        }
    }

    fun allListDelete() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteRecentSearchListUseCase.deleteAllRecentSearchList()
        }
        searchList.value = ApiResult.Success(listOf())
    }
}