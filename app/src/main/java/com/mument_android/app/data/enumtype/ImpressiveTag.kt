package com.mument_android.app.data.enumtype

import androidx.annotation.StringRes
import com.mument_android.R
import java.lang.IllegalArgumentException

enum class ImpressiveTag(val tagIndex: Int, @StringRes val tag: Int) {
    TONE(1, R.string.impressive_tone),
    BEAT(2, R.string.impressive_beat),
    LYRICS(3, R.string.impressive_lyrics),
    MELODY(4, R.string.impressive_melody),
    BASE(5, R.string.impressive_base),
    INTRO(6, R.string.impressive_intro);

    companion object {
        fun findImpressiveStringTag(tagIndex: Int): Int {
            return ImpressiveTag.values().find { it.tagIndex == tagIndex }?.tag
                ?: throw IllegalArgumentException("Cannot find Emotional Tag...")
        }
        fun findImpressiveTagEnum(tagIndex: Int): ImpressiveTag {
            return ImpressiveTag.values().find { it.tagIndex == tagIndex }
                ?: throw IllegalArgumentException("Cannot find Emotional TagEnum...")
        }
    }
}