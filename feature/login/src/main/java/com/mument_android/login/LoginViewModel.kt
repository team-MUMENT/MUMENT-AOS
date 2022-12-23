package com.mument_android.login

import androidx.lifecycle.ViewModel
import com.mument_android.core_dependent.ui.MumentDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel() {

    private fun isFirstCheck() {
        CoroutineScope(Dispatchers.Default).launch {

        }
    }
}