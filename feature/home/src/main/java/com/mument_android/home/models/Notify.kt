package com.mument_android.home.models

import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.home.util.NotifyType

data class Notify(val title:String, val music: Music? = null, val notifyType: NotifyType)