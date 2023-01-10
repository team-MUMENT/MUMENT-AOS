package com.mument_android.home.models

import com.mument_android.home.util.NotifyType

data class Notify(val id:String, val title:String, val createdAt:String, val type:NotifyType, val mumentName:String, val userName:String, val version:String,)