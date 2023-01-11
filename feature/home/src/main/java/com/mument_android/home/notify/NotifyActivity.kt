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
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
                Notify("ajadsklf;jasjlfkhasljksfdjsagfahjgdsajhfgsdasdsadaskldjalkdjalksajdlkasjdklsajdklasjdkl가나다라마바사아가나ㄴㅇdsajdhaskjdsahdkajshasdkjdashjksjkahdasjkhdakjdhkjadshkj"),
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