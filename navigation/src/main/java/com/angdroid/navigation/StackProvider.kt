package com.angdroid.navigation

import com.mument_android.domain.entity.musicdetail.Music
import java.util.Stack

interface StackProvider {
    fun putHistoryBackStack(mumentId: String, userId: Int, music: Music)
    fun popHistoryBackStack()
    fun clearBackStack()
    fun getHistoryBackStack(callback: (Stack<Triple<String, Int, Music>>) -> Unit)
}