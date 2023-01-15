package com.mument_android.home.util

import android.graphics.Typeface
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.annotation.ColorInt

class TextStyleCustomHelper constructor(val text: CharSequence) {
    private val nameNextSuffix = "님이"
    private val musicNextSuffix = "에 쓴 뮤멘트를 좋아합니다."

    fun setEllipsizeMusic(layout: Layout): SpannableStringBuilder? {
        val matcher = "[ㄱ-ㅎ가-힣]".toRegex()
        return if (layout.getEllipsisStart(2) != 0) {
            val ellipsize = layout.getEllipsisCount(2) //라인을 벗어나는 글자의 개수, 무조건 지워야함
            val removeLength =
                //앞 글자가 영어로만 되어있다면, Suffix가 한글을 포함하고 있기 때문에(한글이 너비를 더 차지함) 글자를 더 지워야 Suffix가 차지할 공간이 생김
                text.substring(
                    text.length - (ellipsize + (musicNextSuffix.length)),
                    text.length - ellipsize            //3번째 줄을 넘어가지 않으면서 Suffix가 들어가야하는 글자수만큼만 SubString으로 가져옴
                ).replace(matcher, "")      //한글을 지움(특수문자와 영어만 남김)
            SpannableStringBuilder(
                text.removeRange(
                    text.length - (ellipsize +
                            musicNextSuffix.length +
                            //suffix의 글자수
                            if (removeLength.length - (musicNextSuffix.length * 0.3) > 0) { //suffix가 들어갈 영역의 문자열의 한글을 제외한 문자열의 길이가 suffix의 한글을 제외한 문자열의 길이보다 클 때
                                ((removeLength.length - (musicNextSuffix.length * 0.3)) * 0.6).toInt()  //더 큰 만큼이 한글이 그만큼 있는 것이니 대충 한글 : 영어 = 1.6 : 1 비율로 0.6 곱한만큼을 더 더해주었음
                            } else {
                                -((musicNextSuffix.length * 0.3 - removeLength.length) * 0.6).toInt()   //얘는 반대로 한글이 더 적다는거니깐 덜 빼게 해준다.
                            }),
                    text.length - musicNextSuffix.length
                )
            )
        } else {
            null
        }
    }

    fun applyNotifySpan(
        text: SpannableStringBuilder, @ColorInt nameColor: Int
    ): Boolean {
        val nameEnd = text.indexOf(nameNextSuffix)
        val musicEnd = text.indexOf(nameNextSuffix)
        return if (nameEnd != -1 && musicEnd != -1) {
            text.setSpan(StyleSpan(Typeface.BOLD), 0, nameEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            text.setSpan(
                ForegroundColorSpan(nameColor),
                nameEnd + nameNextSuffix.length,
                musicEnd,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
            true
        } else {
            false
        }
    }

    fun applySuffixesToSpans(   //원하는 글자에 딱 맞게 Span 적용 가능, 해당 기능은 사용 안하지만 필요한 경우 사용
        text: SpannableStringBuilder,
        vararg suffixToStyleToOption: SpanInfo
    ): Boolean {
        var isClear = true
        suffixToStyleToOption.forEach { spanInfo ->
            val matchStart = text.indexOf(spanInfo.suffix.first())
            if (matchStart == -1) {
                isClear = false
                return@forEach
            }
            with(spanInfo) {
                setSpanToMatch(
                    text,
                    suffix.length,
                    matchStart,
                    styleSpan
                )
            }
        }
        return isClear
    }

    private fun setSpanToMatch(      //applySuffixesToSpans()과 같이 쓰는 함수
        text: SpannableStringBuilder,
        suffixSize: Int,
        startIndex: Int,
        style: CharacterStyle
    ) {
        when (style) {
            is StyleSpan -> {
                text.setSpan(
                    style,
                    startIndex,
                    startIndex + suffixSize,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
            is ForegroundColorSpan -> {
                text.setSpan(
                    style,
                    startIndex,
                    startIndex + suffixSize,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
        }
    }
}
