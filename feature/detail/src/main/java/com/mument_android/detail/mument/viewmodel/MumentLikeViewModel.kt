package com.mument_android.detail.mument.viewmodel

import androidx.lifecycle.ViewModel
import com.mument_android.domain.usecase.detail.FetchUsersLikeMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MumentLikeViewModel @Inject constructor(private val fetchUsersLikeMumentUseCase: FetchUsersLikeMumentUseCase) :
    ViewModel() {

    private val fetchMumentId = MutableStateFlow<String>("")
    val usersLikeList = fetchMumentId.flatMapLatest { mumentId ->
        fetchUsersLikeMumentUseCase.invoke(mumentId)
    }

    fun fetchUsersLikeList(mumentId: String) {
        fetchMumentId.value = mumentId
    }
}