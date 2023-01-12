package com.mument_android.home.notify

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.mument_android.home.R

class NotifyEllipsizeCustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
    private val suffix = "에 쓴 뮤멘트를 좋아합니다."//No-break space Symbol 적용 못함,,, Span 풀림,,
    private val matcher = "[ㄱ-ㅎ가-힣]".toRegex()
    var isApplySpan = 0
    override fun draw(canvas: Canvas?) {
        if (text != null) {
            var builder = SpannableStringBuilder(text)
            if (layout.getEllipsisStart(2) != 0) {
                val ellipsize = layout.getEllipsisCount(2) //라인을 벗어나는 글자의 개수, 무조건 지워야함
                val removeLength =
                    //앞 글자가 영어로만 되어있다면, Suffix가 한글을 포함하고 있기 때문에(한글이 너비를 더 차지함) 글자를 더 지워야 Suffix가 차지할 공간이 생김
                    builder.substring(
                        builder.length - (ellipsize + (suffix.length)),
                        builder.length - ellipsize            //3번째 줄을 넘어가지 않으면서 Suffix가 들어가야하는 글자수만큼만 SubString으로 가져옴
                    ).replace(matcher, "")      //한글을 지움(특수문자와 영어만 남김)
                builder = SpannableStringBuilder(
                    builder.removeRange(
                        builder.length - (ellipsize +
                                suffix.length +
                                //suffix의 글자수
                                if (removeLength.length - (suffix.length * 0.3) > 0) { //suffix가 들어갈 영역의 문자열의 한글을 제외한 문자열의 길이가 suffix의 한글을 제외한 문자열의 길이보다 클 때
                                    ((removeLength.length - (suffix.length * 0.3)) * 0.6).toInt()  //더 큰 만큼이 한글이 그만큼 있는 것이니 대충 한글 : 영어 = 1.6 : 1 비율로 0.6 곱한만큼을 더 더해주었음
                                } else {
                                    -((suffix.length * 0.3 - removeLength.length) * 0.6).toInt()   //얘는 반대로 한글이 더 적다는거니깐 덜 빼게 해준다.
                                }),
                        builder.length - suffix.length
                    )
                )
            }
            if (isApplySpan < 2) {   //text = builder 이후 한 번 더 불려야함
                val musicBoldSpan = StyleSpan(Typeface.BOLD)
                val nameIndex = builder.indexOf("님이")  //이름 추출
                val musicIndex = builder.indexOf(suffix)       // 곡 이름 추출
                if (nameIndex != -1 && musicIndex != -1) {
                    val nameColorSpan =
                        ForegroundColorSpan(resources.getColor(R.color.mument_color_blue1))
                    builder.setSpan(
                        nameColorSpan,
                        0,
                        nameIndex,
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    builder.setSpan(
                        musicBoldSpan, nameIndex + 2, musicIndex,
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    text = builder
                    isApplySpan++
                }
            }
        } else {
            isApplySpan = 0
        }
        super.draw(canvas)
    }
}