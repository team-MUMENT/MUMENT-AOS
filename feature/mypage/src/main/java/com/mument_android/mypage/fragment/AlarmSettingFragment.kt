package com.mument_android.mypage.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.core_dependent.util.FirebaseAnalyticsUtil
import com.mument_android.mypage.MyPageViewModel
import com.mument_android.mypage.databinding.FragmentAlarmSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmSettingFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentAlarmSettingBinding>()
    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAlarmSettingBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myPageViewModel = myPageViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        openAppAlarmSetting()
        backBtnListener()
    }

    override fun onResume() {
        super.onResume()
        checkAlarmSetting()
    }

    //앱 내의 알림설정 화면 띄우기
    private fun openAppAlarmSetting() {
        binding.btnAlarmSettingSwitch.setOnClickListener {
            myPageViewModel.alarmSetting.value = binding.btnAlarmSettingSwitch.isSelected
            startActivity(alarmSetting())
        }
    }

    //알림 설정 보여주기
    private fun alarmSetting(): Intent {
        val intent = with(Intent()) {
            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //버전에 따른 알림 설정
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                putExtra(Settings.EXTRA_APP_PACKAGE, activity?.packageName)
            } else {
                putExtra("app_package", activity?.packageName)
                putExtra("app_uid", activity?.applicationInfo?.uid)
            }
        }
        return intent
    }

    //앱 내의 알림설정 여부에 따라 버튼 이미지 변경
    private fun checkAlarmSetting() {
        binding.btnAlarmSettingSwitch.isSelected =
            NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()
        if(myPageViewModel.alarmSetting.value == true && !NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()) {
            //알림 on -> off GA
            FirebaseAnalyticsUtil.firebaseLog(
                "noti_off",
                "count",
                "noti_turn_off_success"
            )
        }
        else if(myPageViewModel.alarmSetting.value == false && NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()) {
            //알림 off -> on GA
            FirebaseAnalyticsUtil.firebaseLog(
                "noti_on",
                "type",
                "noti_page_success"
            )
        }
    }

    private fun backBtnListener() {
        binding.btnAlarmSettingBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
