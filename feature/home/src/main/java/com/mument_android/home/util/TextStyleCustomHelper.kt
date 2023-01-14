package com.mument_android.home.util

import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

class TextStyleCustomHelper constructor(val text: CharSequence) {

    fun setEllipsize(suffix: String, layout: Layout): SpannableStringBuilder? {
        val matcher = "[ㄱ-ㅎ가-힣]".toRegex()
        return if (layout.getEllipsisStart(2) != 0) {
            val ellipsize = layout.getEllipsisCount(2) //라인을 벗어나는 글자의 개수, 무조건 지워야함
            val removeLength =
                //앞 글자가 영어로만 되어있다면, Suffix가 한글을 포함하고 있기 때문에(한글이 너비를 더 차지함) 글자를 더 지워야 Suffix가 차지할 공간이 생김
                text.substring(
                    text.length - (ellipsize + (suffix.length)),
                    text.length - ellipsize            //3번째 줄을 넘어가지 않으면서 Suffix가 들어가야하는 글자수만큼만 SubString으로 가져옴
                ).replace(matcher, "")      //한글을 지움(특수문자와 영어만 남김)
            SpannableStringBuilder(
                text.removeRange(
                    text.length - (ellipsize +
                            suffix.length +
                            //suffix의 글자수
                            if (removeLength.length - (suffix.length * 0.3) > 0) { //suffix가 들어갈 영역의 문자열의 한글을 제외한 문자열의 길이가 suffix의 한글을 제외한 문자열의 길이보다 클 때
                                ((removeLength.length - (suffix.length * 0.3)) * 0.6).toInt()  //더 큰 만큼이 한글이 그만큼 있는 것이니 대충 한글 : 영어 = 1.6 : 1 비율로 0.6 곱한만큼을 더 더해주었음
                            } else {
                                -((suffix.length * 0.3 - removeLength.length) * 0.6).toInt()   //얘는 반대로 한글이 더 적다는거니깐 덜 빼게 해준다.
                            }),
                    text.length - suffix.length
                )
            )
        } else {
            null
        }
    }

    fun applySuffixsToSpans(
        text: SpannableStringBuilder,
        vararg suffixToStyleToOption: SpanInfo
    ) {
        val foundStartIndexs = suffixToStyleToOption.forEach { spanInfo ->
            when (spanInfo.spanType) {
                SpanType.RANGE -> {

                }
                SpanType.MATCH -> {

                }
            }
        }
    }

    fun setSpanToMatch(text: SpannableStringBuilder, suffix: String, style: CharacterStyle) {
        when (style) {
            is StyleSpan -> {
                text.setSpan(style, 1, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            is ForegroundColorSpan -> {

            }
        }
    }

    fun setSpanToRange(text: SpannableStringBuilder, startSuffix: String, endSuffix: String) {

    }

}