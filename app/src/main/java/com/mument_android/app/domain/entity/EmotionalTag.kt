package com.mument_android.app.domain.entity

import java.lang.IllegalArgumentException

sealed class EmotionalTag {
    data class Full(val tagType: Int, val tagString: String): EmotionalTag()
    data class Excited(val tagType: Int, val tagString: String): EmotionalTag()
    data class Flutter(val tagType: Int, val tagString: String): EmotionalTag()
    data class Happy(val tagType: Int, val tagString: String): EmotionalTag()
    data class Confidence(val tagType: Int, val tagString: String): EmotionalTag()
    data class Consolation(val tagType: Int, val tagString: String): EmotionalTag()
    data class Blue(val tagType: Int, val tagString: String): EmotionalTag()
    data class Miss(val tagType: Int, val tagString: String): EmotionalTag()
    data class Loneliness(val tagType: Int, val tagString: String): EmotionalTag()
    data class Dim(val tagType: Int, val tagString: String): EmotionalTag()
    data class Calm(val tagType: Int, val tagString: String): EmotionalTag()
    data class Remembrance(val tagType: Int, val tagString: String): EmotionalTag()
    data class Relaxed(val tagType: Int, val tagString: String): EmotionalTag()
    data class Romance(val tagType: Int, val tagString: String): EmotionalTag()
    data class Stress(val tagType: Int, val tagString: String): EmotionalTag()


    companion object {
        fun findEmotionalType(tagType: Int): EmotionalTag {
            return when(tagType) {
                1 -> Full(tagType, "벅참")
                2 -> Excited(tagType, "신남")
                3 -> Flutter(tagType, "설렘")
                4 -> Happy(tagType, "행복")
                5 -> Confidence(tagType, "자신감")
                6 -> Consolation(tagType, "위로")
                7 -> Blue(tagType, "우울")
                8 -> Miss(tagType, "그리움")
                9 -> Loneliness(tagType, "외로움")
                10 -> Dim(tagType, "아련")
                11 -> Calm(tagType, "차분")
                12 -> Remembrance(tagType, "회상")
                13 -> Relaxed(tagType, "여유로움")
                14 -> Romance(tagType, "낭만")
                15 -> Stress(tagType, "스트레스")
                else -> throw IllegalArgumentException("태그를 찾을 수 없습니다.")
            }
        }
    }
}