package com.mument_android.app.presentation.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music

class MainViewModel : ViewModel() {
    private val _mumentId = MutableLiveData<String>("")
    val mumentId: LiveData<String> = _mumentId
    private val _musicId = MutableLiveData<String>("")
    val musicId: LiveData<String> = _musicId
    private val _music = MutableLiveData<Music>()
    val music: LiveData<Music> = _music

    private val _mumentDetailContents = MutableLiveData<MumentDetailEntity>()
    val mumentDetailContents: LiveData<MumentDetailEntity> = _mumentDetailContents

    fun checkHasBundle() = (!mumentId.value.isNullOrEmpty() && mumentDetailContents.value != null)
    fun checkHasMusic() = (!musicId.value.isNullOrEmpty())
    fun checkMusic() = (music.value != null)

    fun changeMumentId(id: String) {
        _mumentId.value = id
    }

    fun changeMusicId(id: String) {
        _musicId.value = id
    }

    fun changeMusic(music: Music) {
        _music.value = music
    }

    fun changeMumentContents(contents: MumentDetailEntity) {
        _mumentDetailContents.value = contents
    }

    fun clearBundle() {
        _mumentId.value = ""
        _musicId.value = ""
        _music.value = null
        _mumentDetailContents.value = null
    }
}