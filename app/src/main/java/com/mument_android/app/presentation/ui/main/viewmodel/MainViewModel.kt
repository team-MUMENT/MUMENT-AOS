package com.mument_android.app.presentation.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.domain.entity.LimitUserEntity
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.domain.usecase.app.LimitUserUseCase
import com.mument_android.domain.usecase.home.BeforeWhenHomeEnterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val beforeWhenHomeEnterUseCase: BeforeWhenHomeEnterUseCase,
    private val limitUserUseCase: LimitUserUseCase
) : ViewModel() {
    private val _mumentId = MutableLiveData<String>("")
    val mumentId: LiveData<String> = _mumentId

    private val _musicId = MutableLiveData<String>("")
    val musicId: LiveData<String> = _musicId

    private val _music = MutableLiveData<Music>()
    val music: LiveData<Music> = _music

    private val _limitUser = MutableLiveData<LimitUserEntity>()
    val limitUser: LiveData<LimitUserEntity> = _limitUser

    private val _mumentDetailContents = MutableLiveData<MumentDetailEntity>()
    val mumentDetailContents: LiveData<MumentDetailEntity> = _mumentDetailContents

    init {
        viewModelScope.launch {
            beforeWhenHomeEnterUseCase.checkProfileExist().catch { }.collect {
                Timber.e("Profile Exist: ${it.toString()}")
            }
            limitUserUseCase.invoke().let {
                _limitUser.value = it
                Timber.e("isLimitUser: ${it.restricted} ")
            }
        }
    }

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
        /*
        _mumentId.value = ""
        _musicId.value = ""
        _music.value = null
        _mumentDetailContents.value = null

         */
    }
}

