package com.mument_android.app.presentation.ui.main.viewmodel

import androidx.lifecycle.*
import com.mument_android.BuildConfig
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private val _mumentId = MutableLiveData<String>("")
    val mumentId: LiveData<String> = _mumentId
    private val _musicId = MutableLiveData<String>("")
    val musicId: LiveData<String> = _musicId
    private val _music = MutableLiveData<Music>()
    val music: LiveData<Music> = _music

    private val _mumentDetailContents = MutableLiveData<MumentDetailEntity>()
    val mumentDetailContents: LiveData<MumentDetailEntity> = _mumentDetailContents

    fun checkHasMument() = (!mumentId.value.isNullOrEmpty() && mumentDetailContents.value != null)
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


    fun saveTestRefreshToken() {
        viewModelScope.launch {
            dataStoreManager.writeRefreshToken(BuildConfig.TEST_REFRESH_TOKEN)
        }
    }

    fun saveTestAccessToken() {
        viewModelScope.launch {
            dataStoreManager.writeAccessToken(BuildConfig.TEST_ACCESS_TOKEN)
        }
    }

    fun saveTestUserId() {
        viewModelScope.launch {
            dataStoreManager.writeUserId("30")
        }
    }

}