package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.MoveNotifyNavigatorProvider
import com.google.gson.Gson
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.home.notify.NotifyActivity
import com.mument_android.mypage.NoticeDetailActivity
import javax.inject.Inject

class MoveNotifyNavigatorProviderImpl @Inject constructor(val activity: Activity) :
    MoveNotifyNavigatorProvider {
    override fun moveToNoticeDetail(noticeId: Int) {
        with(activity as NotifyActivity) {
            startActivity(Intent(this, NoticeDetailActivity::class.java).apply {
                putExtra("id", noticeId)
            })
        }
    }

    override fun moveToMumentDetail(mumentId: String, musicInfoEntity: MusicInfoEntity) {
        with(activity as NotifyActivity) {
            startActivity(Intent(this, MainActivity::class.java).apply {
                putExtra(MUMENT_ID, mumentId)
                putExtra(MUSIC_INFO_ENTITY, Gson().toJson(musicInfoEntity))
            })
            finish()
        }
    }
}