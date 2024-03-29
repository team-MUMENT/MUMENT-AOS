package com.mument_android.mypage

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import com.angdroid.navigation.QuitMainNavigatorProvider
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.base.WebViewActivity
import com.mument_android.core_dependent.ui.MumentDialogBuilder
import com.mument_android.login.ProfileSettingActivity
import com.mument_android.mypage.databinding.ActivityMyPageBinding
import com.mument_android.mypage.fragment.AlarmSettingFragment
import com.mument_android.mypage.fragment.BlockUserManagementFragment
import com.mument_android.mypage.fragment.NoticeFragment
import com.mument_android.mypage.fragment.UnregisterFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MyPageActivity : BaseActivity<ActivityMyPageBinding>(ActivityMyPageBinding::inflate) {
    private val myPageViewModel: MyPageViewModel by viewModels()


    @Inject
    lateinit var quitMainNavigatorProvider: QuitMainNavigatorProvider

    override fun onStart() {
        super.onStart()
        myPageViewModel.userInfo()
        getVersionInfo()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.myPageViewModel = myPageViewModel

        intent.getBooleanExtra("alarm", false).let {
            if (it) {
                supportFragmentManager.commit() {
                    replace(R.id.fc_my_page, AlarmSettingFragment(), "view")
                    addToBackStack("view")
                }
            }
        }

        moveProfileSetting()
        transactionBtnEvent()
        clickListenerWebView()
        logoutBtnListener()
        moveUnregister()
        userInfoNetwork()
        backBtnEvent()
    }

    private fun moveProfileSetting() {
        binding.clProfile.setOnClickListener {
            Log.e("USER INFO", myPageViewModel.userInfo.value.toString())
            val intent = Intent(this, ProfileSettingActivity::class.java)
            intent.putExtra("nickname", myPageViewModel.userInfo.value?.userName)
            intent.putExtra("img", myPageViewModel.userInfo.value?.image)
            intent.putExtra("checkMyPage", 1)
            startActivity(intent)
        }
    }

    //각 카테고리 버튼 눌렀을 때 이동하는 함수
    private fun transactionBtnEvent() {
        val goNextPageBtn = mutableMapOf(
            binding.clAlarmSetting to AlarmSettingFragment(),
            binding.clBlockUserManagement to BlockUserManagementFragment(),
            binding.clNotice to NoticeFragment()
        )

        goNextPageBtn.forEach { (btn, view) ->
            btn.setOnClickListener {
                supportFragmentManager.commit() {
                    replace(R.id.fc_my_page, view, "view")
                    addToBackStack("view")
                }
            }
            myPageViewModel.isClickBtnEvent(true)
        }
    }

    private fun linkNetwork() {
        myPageViewModel.getWebView("mypage", "")
    }

    //각 웹뷰로 이동
    private fun clickListenerWebView() {
        with(binding) {
            //자주묻는질문
            linkNetwork().also {
                clFAQ.setOnClickListener { initIntent(myPageViewModel?.faq ?: "") }

                //문의하기
                clInquiry.setOnClickListener { sendEmail() }

                //앱정보
                clAppInfo.setOnClickListener { initIntent(myPageViewModel?.appInfo ?: "") }

                //뮤멘트 소개
                clIntroduceMument.setOnClickListener {
                    initIntent(
                        myPageViewModel?.introduction ?: ""
                    )
                }

                //오픈라이선스
                clOpenSource.setOnClickListener { initIntent(myPageViewModel?.license ?: "") }
            }
        }
    }

    private fun userInfoNetwork() {
        myPageViewModel.userInfo.observe(this) {
            binding.viewModel = it
        }
    }

    private fun initIntent(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)
        ContextCompat.startActivity(this, intent, null)
        //startActivity(intent)
    }

    private fun logoutBtnListener() {
        binding.tvMyPageLogout.setOnClickListener {
            MumentDialogBuilder()
                .setHeader(getString(R.string.logout_header))
                .setBody("")
                .setAllowListener("로그아웃") {
                    myPageViewModel.logOut()
                    moveToMainActivity()
                    finish()
                }
                .setCancelListener {}
                .build()
                .show(supportFragmentManager, "hi")
        }
    }

    //회원탈퇴뷰 이동
    private fun moveUnregister() {
        binding.tvMyPageUnregister.setOnClickListener {
            supportFragmentManager.commit() {
                replace(R.id.fc_my_page, UnregisterFragment(), "unregister")
                addToBackStack("unregister")
            }
        }
    }

    private fun backBtnEvent() {
        binding.btnMyPageBack.setOnClickListener {
            finish()
        }
    }

    private fun sendEmail() {
        val email = Intent(Intent.ACTION_SEND)
        email.type = "plain/text"
        val address = arrayOf("mument.mp3@gmail.com")
        val presentVersionInfo = packageManager.getPackageInfo(packageName, 0)
        email.putExtra(Intent.EXTRA_EMAIL, address)
        email.putExtra(Intent.EXTRA_SUBJECT, "[MUMENT] 문의해요 🙋‍♀️")
        email.putExtra(
            Intent.EXTRA_TEXT, "안녕하세요, 뮤멘트입니다. \n" +
                    "문의하실 내용을 하단에 작성해주세요. \n" +
                    "문의에 대한 답변은 전송해주신 메일로 회신드리겠습니다. \n" +
                    "감사합니다. \n" +
                    "—————————————————————————————-\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "—————————————————————————————-\n" +
                    "User: Optional(" + myPageViewModel.id.value + ")\n" +
                    "App Version: " + presentVersionInfo.versionName + "\n" +
                    "OS : " + Build.MODEL + "\n"
        )
        startActivity(email)
    }

    private fun getVersionInfo() {
        val presentVersionInfo = packageManager.getPackageInfo(packageName, 0)
        myPageViewModel.getWebView("version", "AOS")
        myPageViewModel.getWebViewEntity.observe(this) {
            val recentVersionInfo = it.version.toString()
            binding.tvMyPageVersionInfo.text = String.format(
                getString(
                    R.string.my_page_version_info,
                    presentVersionInfo.versionName,
                    recentVersionInfo
                )
            )
            Log.e("mypageRececntVersionInfo", recentVersionInfo)
        }
    }

    private fun moveToMainActivity() {
        quitMainNavigatorProvider.quitMument()
    }
}




