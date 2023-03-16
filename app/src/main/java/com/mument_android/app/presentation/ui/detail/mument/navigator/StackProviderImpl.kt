package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.content.Context
import android.util.Log
import com.mument_android.app.application.MumentApplication
import com.mument_android.detail.mument.listener.StackProvider
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class StackProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
): StackProvider {
    override fun putHistoryBackStack(mumentId: String, userId: Int,  music: Music) {
        (context as MumentApplication).historyBackStack.push(Triple(mumentId ,userId , music))
    }

    override fun popHistoryBackStack() {
        (context as MumentApplication).historyBackStack.let { stack ->
            if (stack.isNotEmpty()) stack.pop()
        }
    }

    override fun getHistoryBackStack(callback: (Stack<Triple<String, Int, Music>>) -> Unit) {
        (context as MumentApplication).historyBackStack.let {
            callback(it)
        }
    }
}