package com.mument_android.app.data.enumtype

import androidx.annotation.StringRes
import com.mument_android.R
import java.lang.IllegalArgumentException

/**
 * Enum Class는 열거형 함수인데 kotlin 이외에도 여러 언어에 존재합니다.
 * 보통 상수들을 저장하는데 쓰이고,
 * 추상 메서드 등을 이용하면 같은 Input에 대해 다른 Output을 발생시키는 로직들을 간편하게 짤 수 있습니다!!
 * (잘 이용하면 분기처리들을 쉽게 할 수 있어요!)
 */

enum class EmotionalTag(val tagIndex: Int, @StringRes val tag: Int) {
    Full(1,R.string.emotional_full),
    Excited(2, R.string.emotional_excited),
    Flutter(3, R.string.emotional_flutter),
    Happy(4, R.string.emotional_happy),
    Confidence(5, R.string.emotional_confidence),
    Consolation(6, R.string.emotional_consolation),
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
        /**
         * values() 함수를 통해서 Enum Class에 정의된 모든 인스턴스들을 가져올 수 있습니다!
         * 함수에 파라미터로 tagIndex를 가져와서 같은 tagIndex를 가진 Enum의 tag를 return 하도록 했습니다.
         */
        fun findEmotionalStringTag(tagIndex: Int): Int {
            return values().find { it.tagIndex == tagIndex }?.tag
                ?: throw IllegalArgumentException("Cannot find Emotional Tag...")
        }

        fun findEmotionalTagEnum(tagIndex: Int): EmotionalTag {
            return values().find { it.tagIndex == tagIndex }
                ?: throw IllegalArgumentException("Cannot find Emotional Tag Enum...")
        }

    }
}