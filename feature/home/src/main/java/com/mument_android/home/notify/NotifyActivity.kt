package com.mument_android.home.notify

import android.os.Bundle
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.home.R
import com.mument_android.home.adapters.NotifyAdapter
import com.mument_android.home.databinding.ActivityNotifyBinding
import com.mument_android.home.models.Notify

class NotifyActivity : BaseActivity<ActivityNotifyBinding>(R.layout.activity_notify) {

    lateinit var notifyAdapter: NotifyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        appBarClickListener()
        notifyAdapter = NotifyAdapter()
        binding.rvNotifyList.adapter = notifyAdapter
        notifyAdapter.submitList(
            listOf(
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
                Notify("가나다라마나암ㅇㅁㄴ아ㅣㅁ너아ㅣㄴ어ㅇ녀로ㅓㅇㄴㅁㄹ언모리ㅏㅁ노ㅓㅏ흔들리는 ㄱ꽃들 속에서~~ 내 샴푸향이 느껴진거일겨~~~가나다라마바사아자차카내 샴푸향이 느껴진거일겨내 샴푸향이 느껴진거일겨"),
            )
        )

    }

    private fun appBarClickListener() {
        binding.appbarNotify.ivNotifyBack.setOnClickListener {
            finish()
        }
        binding.appbarNotify.ivNotifySetting.setOnClickListener {

        }
    }
}