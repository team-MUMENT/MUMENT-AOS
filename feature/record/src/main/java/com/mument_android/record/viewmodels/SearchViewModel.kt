package com.mument_android.record.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.usecase.home.CRURecentSearchListUseCase
import com.mument_android.domain.usecase.home.DeleteRecentSearchListUseCase
import com.mument_android.domain.usecase.home.SearchMusicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val cruRecentSearchListUseCase: CRURecentSearchListUseCase,
    private val deleteRecentSearchListUseCase: DeleteRecentSearchListUseCase,
    private val searchMusicUseCase: SearchMusicUseCase
) : ViewModel() {
    private val _searchList = MutableStateFlow<List<RecentSearchData>?>(null)
    val searchList get() = _searchList.asStateFlow()
    val searchText = MutableLiveData<String>("")
    private val _searchResultList = MutableStateFlow<List<RecentSearchData>?>(null)
    val searchResultList get() = _searchResultList.asStateFlow()
    val searchOption = MutableStateFlow(false)

    init {
        setRecentData()
    }

    fun selectContent(data: RecentSearchData) {
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

    fun searchSwitch(option: Boolean) {
        if (!option) _searchResultList.value = listOf()
        searchOption.value = option
    }

    private fun setRecentData() {
        viewModelScope.launch {
            cruRecentSearchListUseCase.getAllRecentSearchList().catch {
                _searchList.value = null
            }.collect {
                _searchList.value = it
            }
        }
    }

    fun searchMusic(keyword: String) {
        viewModelScope.launch {
            searchMusicUseCase.searchMusic(keyword).catch {
                _searchResultList.value = null
            }.collect {
                searchText.value = keyword
                _searchResultList.value = it
                searchOption.value = true
            }
        }
    }

    private fun insertOrUpdateRecentItem(data: RecentSearchData) {
        viewModelScope.launch {
            cruRecentSearchListUseCase.insertOrUpdateRecentSearchItem(data)
        }
    }

    fun deleteRecentList(data: RecentSearchData) {
        viewModelScope.launch {
            deleteRecentSearchListUseCase.deleteRecentSearchItem(data)
            val temp = searchList.value?.toMutableList()
            temp?.remove(data)
            temp?.let {
                _searchList.value = it
            }
        }
    }

    fun clearSearchResult() {
        setRecentData()
        _searchResultList.value = null
    }

}