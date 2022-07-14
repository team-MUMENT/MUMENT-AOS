package com.mument_android.app.presentation.ui.locker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.entity.MumentCardData.Music
import com.mument_android.app.domain.entity.MumentCardData.User
import com.mument_android.app.domain.entity.TestLockerMumentCard

class LockerViewModel : ViewModel() {

    //TEST CODE
    val mument = listOf<MumentCard>(
        MumentCard(
            "213", "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
            "1 Sep, 2020",
            Music(
                "213",
                "백예린",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "Antifreeze"
            ),
            User(
                "123",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "이수지"
            ),
            null, null, null, null, null, "2020", "1"
        ),
        MumentCard(
            "213", "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
            "1 Sep, 2020",
            Music(
                "213",
                "백예린",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "Antifreeze"
            ),
            User(
                "123",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "이수지"
            ),
            null, null, null, null, null, "2020", "1"
        ),
        MumentCard(
            "213", "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
            "1 Sep, 2020",
            Music(
                "213",
                "백예린",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "Antifreeze"
            ),
            User(
                "123",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "이수지"
            ),
            null, null, null, null, null,  "2020", "1"
        ),
        MumentCard(
            "213", "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
            "1 Sep, 2020",
            Music(
                "213",
                "백예린",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "Antifreeze"
            ),
            User(
                "123",
                "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
                "이수지"
            ),
            null, null, null, null, null, "2020", "2"
        )

    )
}