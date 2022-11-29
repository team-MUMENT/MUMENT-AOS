package com.mument_android.detail.mument

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.detail.R
import com.mument_android.detail.databinding.ActivityDeclarMumentBinding
import com.mument_android.detail.viewmodels.MumentDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeclarMumentActivity :
    BaseActivity<ActivityDeclarMumentBinding>(R.layout.activity_declar_mument) {

    private val viewModel: MumentDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        backBtnListener()
        checkBoxListener()
        with(binding) {
            setRadioBtn(
                clFirstReason,
                clSecondReason,
                clThirdReason,
                clForthReason,
                clFifthReason,
                clSixthReason,
                clSeventhReason
            )
        }
        nextBtnListener()
    }

    private fun backBtnListener() {
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }

    private fun setRadioBtn(
        view1: View,
        view2: View,
        view3: View,
        view4: View,
        view5: View,
        view6: View,
        view7: View
    ) {
        view1.setOnClickListener {
            if (!view1.isSelected) {
                view1.isSelected = true
                view2.isSelected = false
                view3.isSelected = false
                view4.isSelected = false
                view5.isSelected = false
                view6.isSelected = false
                view7.isSelected = false
            }
            binding.tvNotifyFinish.isEnabled = true
        }
        view2.setOnClickListener {
            if (!view2.isSelected) {
                view1.isSelected = false
                view2.isSelected = true
                view3.isSelected = false
                view4.isSelected = false
                view5.isSelected = false
                view6.isSelected = false
                view7.isSelected = false
            }
            binding.tvNotifyFinish.isEnabled = true
        }
        view3.setOnClickListener {
            if (!view3.isSelected) {
                view1.isSelected = false
                view2.isSelected = false
                view3.isSelected = true
                view4.isSelected = false
                view5.isSelected = false
                view6.isSelected = false
                view7.isSelected = false
            }
            binding.tvNotifyFinish.isEnabled = true
        }
        view4.setOnClickListener {
            if (!view4.isSelected) {
                view1.isSelected = false
                view2.isSelected = false
                view3.isSelected = false
                view4.isSelected = true
                view5.isSelected = false
                view6.isSelected = false
                view7.isSelected = false
            }
            binding.tvNotifyFinish.isEnabled = true
        }
        view5.setOnClickListener {
            if (!view5.isSelected) {
                view1.isSelected = false
                view2.isSelected = false
                view3.isSelected = false
                view4.isSelected = false
                view5.isSelected = true
                view6.isSelected = false
                view7.isSelected = false
            }
            binding.tvNotifyFinish.isEnabled = true
        }
        view6.setOnClickListener {
            if (!view6.isSelected) {
                view1.isSelected = false
                view2.isSelected = false
                view3.isSelected = false
                view4.isSelected = false
                view5.isSelected = false
                view6.isSelected = true
                view7.isSelected = false
            }
            binding.tvNotifyFinish.isEnabled = true
        }
        view7.setOnClickListener {
            if (!view7.isSelected) {
                view1.isSelected = false
                view2.isSelected = false
                view3.isSelected = false
                view4.isSelected = false
                view5.isSelected = false
                view6.isSelected = false
                view7.isSelected = true
            }
            binding.tvNotifyFinish.isEnabled = true
        }

    }

    private fun checkBoxListener() {
        binding.clBlock.setOnClickListener {
            binding.ivBlockCheck.isSelected = !binding.ivBlockCheck.isSelected
        }
    }


    private fun nextBtnListener() {
        binding.tvNotifyFinish.setOnClickListener {
            //TODO : 신고 api연결
            finish()
        }
    }

}