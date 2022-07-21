package com.mument_android.app.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.entity.TempBannerData
import com.mument_android.app.domain.usecase.home.WhenHomeEnterUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val useCase:WhenHomeEnterUseCase) : ViewModel() {
    val mument = listOf<MumentCard>(
//        MumentCard(
//            "213",
//            "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
//            "1 Sep, 2020",
//            "213",
//            "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
//            "Antifreeze",
//            "백예린",
//            "123",
//            "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
//            "이수지",
//            null, null, null, null, "우울", "우울"
//        ), MumentCard(
//            "213",
//            "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
//            "1 Sep, 2020",
//            "213",
//            "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
//            "Antifreeze",
//            "백예린",
//            "123",
//            "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
//            "이수지",
//            12, null, null, null, "우울", "우울"
//        ), MumentCard(
//            "213",
//            "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
//            "1 Sep, 2020",
//            "213",
//            "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
//            "Antifreeze",
//            "백예린",
//            "123",
//            "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
//            "이수지",
//            12, null, null, null, "우울", "우울"
//        )
    )
    val bannerData = listOf<TempBannerData>(
        TempBannerData(
            "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
            "그리움이 느껴지는 곡",
            "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
            "민수는 혼란스럽다",
            "민수"
        ),
        TempBannerData(
            "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
            "그리움이 느껴지는 곡",
            "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
            "민수는 혼란스럽다",
            "민수"
        ),
        TempBannerData(
            "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
            "그리움이 느껴지는 곡",
            "https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",
            "민수는 혼란스럽다",
            "민수"
        )
    )
    private var bannerNum = 0
    val bannerNumIncrease = flow {
        while (true) {
            bannerNum = ++bannerNum
            delay(3000)
            emit(bannerNum)
        }
    }

    fun bannerIndexChange(position: Int) {
        bannerNum = position
    }

}