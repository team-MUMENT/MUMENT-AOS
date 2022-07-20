package com.mument_android.app.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.usecase.home.CRURecentSearchListUseCase
import com.mument_android.app.domain.usecase.home.DeleteRecentSearchListUseCase
import com.mument_android.app.domain.usecase.home.SearchMusicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val cruRecentSearchListUseCase: CRURecentSearchListUseCase,
    private val deleteRecentSearchListUseCase: DeleteRecentSearchListUseCase,
    private val searchMusicUseCase: SearchMusicUseCase
) :
    ViewModel() {
    val searchList = MutableStateFlow<ApiResult<List<RecentSearchData>>?>(null)

    val searchContent = MutableStateFlow<RecentSearchData?>(null)
    val searchResultList = MutableStateFlow<ApiResult<List<RecentSearchData>>?>(null)
    fun selectContent(data: RecentSearchData) {
        searchContent.value = data
        insertOrUpdateRecentItem(
            RecentSearchData(
                data._id,
                data.artist,
                data.image,
                data.name,
                Date(System.currentTimeMillis())
            )
        )
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
                searchList.value = ApiResult.Success(it)
            }
        }
    }

    fun searchMusic(keyword: String) {

        viewModelScope.launch {
            searchMusicUseCase.searchMusic(keyword).onStart {
                searchResultList.value = ApiResult.Loading(null)
            }.catch {
                searchResultList.value = ApiResult.Failure(null)
            }.collect {
                searchResultList.value = ApiResult.Success(it)
            }

        }
    }

    suspend fun selectMusicList(data: RecentSearchData) = withContext(Dispatchers.IO) {
        cruRecentSearchListUseCase.insertOrUpdateRecentSearchItem(data)
    }

    fun insertOrUpdateRecentItem(data: RecentSearchData) {
        viewModelScope.launch(Dispatchers.IO) {
            cruRecentSearchListUseCase.insertOrUpdateRecentSearchItem(data)
        }
    }

    fun deleteRecentList(data: RecentSearchData) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteRecentSearchListUseCase.deleteRecentSearchItem(data).let { setRecentData(this) }
        }
    }

    fun allListDelete() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteRecentSearchListUseCase.deleteAllRecentSearchList()
            setRecentData(this)
        }
    }
}