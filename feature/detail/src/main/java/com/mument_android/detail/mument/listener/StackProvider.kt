package com.mument_android.detail.mument.listener

import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import java.util.Stack

interface StackProvider {
    fun putHistoryBackStack(userId: Int, music: Music)
    fun popHistoryBackStack()
    fun getHistoryBackStack(callback: (Stack<Pair<Int, Music>>) -> Unit)
}