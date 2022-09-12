package com.startup.core_dependent.util

import androidx.annotation.StringRes
import com.startup.core_dependent.R
import java.lang.IllegalArgumentException

/**
 * Enum Class는 열거형 함수인데 kotlin 이외에도 여러 언어에 존재합니다.
 * 보통 상수들을 저장하는데 쓰이고,
 * 추상 메서드 등을 이용하면 같은 Input에 대해 다른 Output을 발생시키는 로직들을 간편하게 짤 수 있습니다!!
 * (잘 이용하면 분기처리들을 쉽게 할 수 있어요!)
 */

enum class EmotionalTag(val tagIndex: Int, @StringRes val tag: Int) {
    Full(200, R.string.emotional_full),
    Excited(201, R.string.emotional_excited),
    Flutter(202, R.string.emotional_flutter),
    Happy(203, R.string.emotional_happy),
    Confidence(204, R.string.emotional_confidence),
    Relaxed(205, R.string.emotional_relaxed),
    Centimeter(206, R.string.emotional_centimeter),
    Blue(207, R.string.emotional_blue),
    Miss(208, R.string.emotional_miss),
    Loneliness(209, R.string.emotional_loneliness),
    Stress(210, R.string.emotional_stress),
    Dim(211, R.string.emotional_dim),
    Remembrance(212, R.string.emotional_remembrance),
    Consolation(213, R.string.emotional_consolation),
    Romance(214, R.string.emotional_romance),
    Calm(215, R.string.emotional_calm);



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