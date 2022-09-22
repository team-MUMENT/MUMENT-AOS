package com.startup.record.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.startup.domain.entity.home.RecentSearchData
import com.startup.domain.usecase.home.CRURecentSearchListUseCase
import com.startup.domain.usecase.home.DeleteRecentSearchListUseCase
import com.startup.domain.usecase.home.SearchMusicUseCase
import com.startup.core.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
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
    val searchText = MutableLiveData<String>("")
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
            setRecentData(this)
        }
        searchList.value = ApiResult.Success(listOf())
    }
}