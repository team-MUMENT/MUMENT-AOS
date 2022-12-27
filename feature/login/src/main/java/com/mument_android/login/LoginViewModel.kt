package com.mument_android.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    //private val signTestUseCase: SignTestUseCase
) : ViewModel() {

    /*
    val isFirstLaunch: Boolean = signTestUseCase.getFirstLaunch()

    fun setFirstLaunch(isFirstLaunch: Boolean) =
        viewModelScope.launch {
            signTestUseCase.saveFirstLaunch(isFirstLaunch)
        }

     */
}