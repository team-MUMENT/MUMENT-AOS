package com.example.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import com.example.mypage.databinding.FragmentAlarmSettingBinding
import com.mument_android.core_dependent.util.AutoClearedValue

class AlarmSettingFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentAlarmSettingBinding>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAlarmSettingBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }



    class SettingPreferenceFragment : PreferenceFragmentCompat(){
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.setting_alarm_preference)
        }

    }

}