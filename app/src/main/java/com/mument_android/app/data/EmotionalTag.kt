package com.mument_android.app.data

import android.content.Context
import androidx.annotation.StringRes
import com.mument_android.R
import java.lang.IllegalArgumentException

enum class EmotionalTag(val tagIndex: Int, val emoji: String, @StringRes val tag: Int) {
    Full(1, "\uD83C\uDFA1",R.string.emotional_full),
    Excited(2, "\uD83D\uDE04", R.string.emotional_excited),
    Flutter(3, "\uD83D\uDC90", R.string.emotional_flutter),
    Happy(4, "\uD83D\uDE1A", R.string.emotional_happy),
    Confidence(5, "\uD83D\uDE4C", R.string.emotional_confidence),
    Consolation(6,"\uD83D\uDC65", R.string.emotional_consolation),
    Blue(7, "\uD83D\uDE14", R.string.emotional_blue),
    Miss(8,"\uD83D\uDD70",  R.string.emotional_miss),
    Loneliness(9,"\uD83D\uDECC",  R.string.emotional_loneliness),
    Dim(10, "⌛", R.string.emotional_dim),
    Calm(11, "☕️", R.string.emotional_calm),
    Remembrance(12, "\uD83D\uDCAD", R.string.emotional_remembrance),
    Relaxed(13, "\uD83C\uDF40", R.string.emotional_relaxed),
    Romance(14,"\uD83C\uDF05",  R.string.emotional_romance),
    Stress(15,"\uD83C\uDF0B",  R.string.emotional_stress);

    companion object {
        fun findEmotionalTag(tagIndex: Int): String {
            return values().find { it.tagIndex == tagIndex }?.emoji
                ?: throw IllegalArgumentException("")
        }
    }
}