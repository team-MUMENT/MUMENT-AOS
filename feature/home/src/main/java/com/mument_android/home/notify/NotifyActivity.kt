package com.mument_android.home.notify

import android.os.Bundle
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.home.R
import com.mument_android.home.databinding.ActivityNotifyBinding

class NotifyActivity : BaseActivity<ActivityNotifyBinding>(R.layout.activity_notify) {

    private val suffix = "...에 쓴 뮤멘트를 좋아합니다"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appBarClickListener()
        /*binding.tvHi.post {
            if (binding.tvHi.layout.getEllipsisStart(2) != 0) {
                val newText = binding.tvHi.text.removeRange(
                    binding.tvHi.text.length - (binding.tvHi.layout.getEllipsisCount(2) + (suffix.length)),
                    binding.tvHi.text.length
                )
                binding.tvHi.text = String.format("%s%s", newText, suffix)
            }
        }*/
    }

    private fun appBarClickListener() {
        binding.appbarNotify.ivNotifyBack.setOnClickListener {
            finish()
        }
        binding.appbarNotify.ivNotifySetting.setOnClickListener {

        }
    }
}