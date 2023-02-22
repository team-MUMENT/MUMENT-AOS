package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import com.angdroid.navigation.MoveNotifyNavigatorProvider
import com.google.gson.Gson
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.core.util.Constants.START_NAV_KEY
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

    override fun moveToMumentDetail(mumentId: String, musicInfoEntity: MusicInfoEntity, startNav: String) {
        with(activity as NotifyActivity) {
            Intent().apply {
                putExtra(START_NAV_KEY, startNav)
                putExtra(MUMENT_ID, mumentId)
                putExtra(MUSIC_INFO_ENTITY, Gson().toJson(musicInfoEntity))
                setResult(RESULT_OK, this)
                finish()
            }
        }
    }
}