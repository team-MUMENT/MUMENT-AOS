package com.mument_android.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.domain.usecase.sign.SignTestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signTestUseCase: SignTestUseCase
) : ViewModel() {


    val isFirstLaunch: Boolean = signTestUseCase.getFirstLaunch()

    fun setFirstLaunch(isFirstLaunch: Boolean) =
        viewModelScope.launch {
            signTestUseCase.saveFirstLaunch(isFirstLaunch)
        }


}