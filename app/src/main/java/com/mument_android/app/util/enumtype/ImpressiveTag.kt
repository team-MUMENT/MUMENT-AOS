package com.mument_android.app.util.enumtype

import androidx.annotation.StringRes
import com.mument_android.R
import java.lang.IllegalArgumentException

enum class ImpressiveTag(val tagIndex: Int, @StringRes val tag: Int) {
    TONE(100, R.string.impressive_tone),
    BEAT(101, R.string.impressive_beat),
    LYRICS(102, R.string.impressive_lyrics),
    MELODY(103, R.string.impressive_melody),
    BASE(104, R.string.impressive_base),
    INTRO(105, R.string.impressive_intro);

    companion object {
        fun findImpressiveStringTag(tagIndex: Int): Int {
            return ImpressiveTag.values().find { it.tagIndex == tagIndex }?.tag
                ?: throw IllegalArgumentException("Cannot find Impression Tag...")
        }
        fun findImpressiveTagEnum(tagIndex: Int): ImpressiveTag {
            return ImpressiveTag.values().find { it.tagIndex == tagIndex }
                ?: throw IllegalArgumentException("Cannot find Impression TagEnum...")
        }
    }
}