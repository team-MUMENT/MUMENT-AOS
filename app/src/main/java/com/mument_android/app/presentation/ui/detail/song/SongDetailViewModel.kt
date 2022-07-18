package com.mument_android.app.presentation.ui.detail.song

import androidx.lifecycle.ViewModel
import com.mument_android.app.data.dto.MumentCard
import com.mument_android.app.domain.entity.MumentCardData.Music
import com.mument_android.app.domain.entity.MumentCardData.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SongDetailViewModel: ViewModel() {
    private val _songName = MutableStateFlow("")
    val songName = _songName.asStateFlow()

    private val _testImage = MutableStateFlow("https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize")
    val testImage = _testImage.asStateFlow()

    private val _dummyMyMuments = MutableStateFlow<MumentCard?>(null)
    val dummyMyMuments = _dummyMyMuments.asStateFlow()

    private val _everyMuments = MutableStateFlow<List<MumentCard>>(listOf())
    val everyMuments = _everyMuments.asStateFlow()

    private val _selectedSort = MutableStateFlow("")
    val selectedSort = _selectedSort.asStateFlow()

    init {
        fetchDummyMument()
        fetchDummyEveryMuments()
    }

    fun changeSelectedSort(sort: String) {
        _selectedSort.value = sort
    }

    private fun fetchDummyMument() {
        _dummyMyMuments.value = MumentCard(
            "213",
            "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
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
            15, null, null, null, null, null, null
        )
    }

    private fun fetchDummyEveryMuments() {
        _everyMuments.value = listOf(
            MumentCard(
                "213",
                "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
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
                15, null, null, null, null, null, null
            ),
            MumentCard(
                "213",
                "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
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
                15, null, null, null, null, null, null
            ),
            MumentCard(
                "213",
                "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
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
                15, null, null, null, null, null, null
            ),
            MumentCard(
                "213",
                "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게",
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
                15, null, null, null, null, null, null
            )
        )
    }
}