package com.mument_android.app.presentation.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.app.domain.entity.musicdetail.MusicDetailEntity
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.MyMument
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.User

class MusicDetailViewModel : ViewModel() {

    val musicDetailData = MutableLiveData<MusicDetailEntity>(
        MusicDetailEntity(
            Music(
                "123",
                "혁오",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "Love ya!"
            ), MyMument(
                "123",
                "음악은 저에게 영감을 줘요. 이 곡 추천해준 이부장에게 심심한 감사를",
                "1, Sep 2020",
                listOf(1, 3, 4),
                listOf(5, 9, 6),
                false,
                false,
                false,
                false,
                14,
                User(
                    "21",
                    "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                    "이수지"
                )
            )
        )
    )

}