package com.mument_android.mypage.fragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.commit
import androidx.preference.PreferenceFragmentCompat
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.mypage.R
import com.mument_android.mypage.databinding.FragmentAlarmSettingBinding

class AlarmSettingFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentAlarmSettingBinding>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAlarmSettingBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPreferenceFragment()
        openAppAlarmSetting(requireContext())

    }

    //설정 프래그먼트 fragment container 에 세팅
    private fun setPreferenceFragment() {
        childFragmentManager.commit {
            add(R.id.fc_preference_fragment, SettingPreferenceFragment())
        }
    }

    //앱 내의 알림설정 화면 띄우기
    private fun openAppAlarmSetting(context: Context) {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            alarmSettingVersionUp(context)
        } else {
            alarmSettingVersionDown(context)
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    //API 26 이상일 떄의  내 알림 설정 보여주기
    @RequiresApi(Build.VERSION_CODES.O)
    private fun alarmSettingVersionUp(context: Context): Intent {
        return Intent().apply {
            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

    // API 26 이하 일 때
    private fun alarmSettingVersionDown(context: Context): Intent {
        return Intent().apply {
            action = "android.settings.APP_NOTIFICATION_SETTINGS"
            putExtra("app_package", context.packageName)
            putExtra("app_uid",context.applicationInfo?.uid)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }





    class SettingPreferenceFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.setting_alarm_preference, rootKey)
        }
    }

}