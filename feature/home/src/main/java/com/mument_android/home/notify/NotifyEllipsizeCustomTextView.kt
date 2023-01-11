package com.mument_android.home.notify

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class NotifyEllipsizeCustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
    private val suffix = "...에 쓴 뮤멘트를 좋아합니다."
    private val matcher = "[ㄱ-ㅎ가-힣]".toRegex()

    override fun onDraw(canvas: Canvas?) {             //부모의 onDraw()가 호출 되기 전에 text를 수정해야함(그래야 그리는 동작을 하기 전에 text를 박아놓으니)
        if (layout.getEllipsisStart(2) != 0) {
            val ellipsize = layout.getEllipsisCount(2) //라인을 벗어나는 글자의 개수, 무조건 지워야함
            val removeLength =
                //앞 글자가 영어로만 되어있다면, Suffix가 한글을 포함하고 있기 때문에(한글이 너비를 더 차지함) 글자를 더 지워야 Suffix가 차지할 공간이 생김
                text.substring(
                    text.length - (ellipsize + (suffix.length)),
                    text.length - ellipsize            //3번째 줄을 넘어가지 않으면서 Suffix가 들어가야하는 글자수만큼만 SubString으로 가져옴
                ).replace(matcher, "")      //한글을 지움(특수문자와 영어만 남김)
            val newText = text.removeRange(
                text.length - (ellipsize +
                        suffix.length +                 //suffix의 글자수
                        if (removeLength.length - (suffix.length * 0.4) > 0) { //suffix가 들어갈 영역의 문자열의 한글을 제외한 문자열의 길이가 suffix의 한글을 제외한 문자열의 길이보다 클 때
                            ((removeLength.length - (suffix.length * 0.4)) * 0.6).toInt()  //더 큰 만큼이 한글이 그만큼 있는 것이니 대충 한글 : 영어 = 1.6 : 1 비율로 0.6 곱한만큼을 더 더해주었음
                        } else {
                            -((suffix.length * 0.4 - removeLength.length) * 0.6).toInt()   //얘는 반대로 한글이 더 적다는거니깐 덜 빼게 해준다.
                        }),
                text.length
            )
            text = "$newText$suffix" //완벽하진 않아도 거의 끝 정도까지 갈 수 있음.
        }
        super.onDraw(canvas)
    }
}