package com.mument_android.app.data

import com.mument_android.R
import java.lang.IllegalArgumentException

enum class EmotionalTag(val tagIndex: Int,  val stringTag: Int) {
    Full(1, R.string.emotional_full),
    Excited(2, R.string.emotional_excited),
    Flutter(3, R.string.emotional_flutter),
    Happy(4, R.string.emotional_happy),
    Confidence(5, R.string.emotional_confidence),
    Consolation(6,R.string.emotional_consolation),
    Blue(7, R.string.emotional_blue),
    Miss(8, R.string.emotional_miss),
    Loneliness(9, R.string.emotional_loneliness),
    Dim(10, R.string.emotional_dim),
    Calm(11, R.string.emotional_calm),
    Remembrance(12, R.string.emotional_remembrance),
    Relaxed(13, R.string.emotional_relaxed),
    Romance(14, R.string.emotional_romance),
    Stress(15, R.string.emotional_stress);

    companion object {
        fun findStringTag(tagIndex: Int): Int {
            return values().find { it.tagIndex == tagIndex }?.stringTag
                ?: throw IllegalArgumentException("Cannot found emotional tag")
        }
    }
}