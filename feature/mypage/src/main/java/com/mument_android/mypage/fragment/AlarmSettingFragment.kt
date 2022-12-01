package com.mument_android.mypage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
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

            childFragmentManager.commit {
                add(R.id.fc_preference_fragment, SettingPreferenceFragment())
            }

    }

    class SettingPreferenceFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.setting_alarm_preference, rootKey)
        }
    }

}