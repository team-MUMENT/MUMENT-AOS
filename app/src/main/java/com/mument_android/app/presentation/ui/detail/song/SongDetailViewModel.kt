package com.mument_android.app.presentation.ui.detail.song

import androidx.lifecycle.ViewModel
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.entity.musicdetail.MusicDetailEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SongDetailViewModel: ViewModel() {
    private val _songName = MutableStateFlow("")
    val songName = _songName.asStateFlow()

    private val _testImage = MutableStateFlow("https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize")
    val testImage = _testImage.asStateFlow()

    private val _dummyMyMuments = MutableStateFlow<MumentCard?>(null)
    val dummyMyMuments = _dummyMyMuments.asStateFlow()

    private val _everyMuments = MutableStateFlow<List<MusicDetailEntity>>(listOf())
    val everyMuments = _everyMuments.asStateFlow()

    private val _selectedSort = MutableStateFlow("")
    val selectedSort = _selectedSort.asStateFlow()

    init {
        fetchDummyMument()
    }

    fun changeSelectedSort(sort: String) {
        _selectedSort.value = sort
    }

    private fun fetchDummyMument() {

    }

    fun fetchDummyEveryMuments() {
        _everyMuments.value = listOf()
    }
}