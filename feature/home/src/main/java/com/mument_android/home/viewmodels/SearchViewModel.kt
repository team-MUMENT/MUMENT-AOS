package com.mument_android.home.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.network.ApiResult
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.usecase.home.CRURecentSearchListUseCase
import com.mument_android.domain.usecase.home.DeleteRecentSearchListUseCase
import com.mument_android.domain.usecase.home.SearchMusicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
    private val _searchList = MutableStateFlow<ApiResult<List<RecentSearchData>>?>(null)
    val searchList get() = _searchList.asStateFlow()
    val searchText = MutableLiveData<String>("")
    private val _searchContent = MutableStateFlow<RecentSearchData?>(null)
    private val _searchResultList = MutableStateFlow<ApiResult<List<RecentSearchData>>?>(null)
    val searchResultList get() = _searchResultList.asStateFlow()
    val searchOption = MutableStateFlow(false)

    init {
        setRecentData()
    }

    fun selectContent(data: RecentSearchData) {
        _searchContent.value = data
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

    fun setRecentData() {
        viewModelScope.launch(Dispatchers.IO) {
            cruRecentSearchListUseCase.getAllRecentSearchList().catch {
                _searchList.value = ApiResult.Failure(null)
            }.collect {
                if (it != null) {
                    _searchList.value = ApiResult.Success(it)
                }
            }
        }
    }

    fun searchSwitch(option: Boolean) {
        if (!option) _searchResultList.value = ApiResult.Success(listOf())
        searchOption.value = option
    }

    fun searchMusic(keyword: String) {
        viewModelScope.launch {
            searchMusicUseCase.searchMusic(keyword).catch {
                _searchResultList.value = ApiResult.Failure(null)
            }.collect {
                if (it != null) {
                    if (it.isEmpty()) {
                        searchText.value = keyword
                    }
                    _searchResultList.value = ApiResult.Success(it)
                } else {
                    searchText.value = keyword
                }
            }
        }
    }

    private fun insertOrUpdateRecentItem(data: RecentSearchData) {
        viewModelScope.launch(Dispatchers.IO) {
            cruRecentSearchListUseCase.insertOrUpdateRecentSearchItem(data)
        }
    }

    fun deleteRecentList(data: RecentSearchData) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteRecentSearchListUseCase.deleteRecentSearchItem(data)
            val temp = searchList.value?.data?.toMutableList()
            temp?.remove(data)
            temp?.let {
                _searchList.value = ApiResult.Success(it)
            }
            // 깜빡 거리는 이슈 있음
            //setRecentData()
        }
    }

    fun allListDelete() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteRecentSearchListUseCase.deleteAllRecentSearchList()
            setRecentData()
        }
        _searchList.value = ApiResult.Success(listOf())
    }
}