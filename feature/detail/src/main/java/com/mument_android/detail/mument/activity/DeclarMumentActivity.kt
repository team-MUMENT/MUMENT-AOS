package com.mument_android.detail.mument.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.detail.R
import com.mument_android.detail.databinding.ActivityDeclarMumentBinding
import com.mument_android.detail.mument.viewmodel.MumentDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeclarMumentActivity :
    BaseActivity<ActivityDeclarMumentBinding>(inflate = ActivityDeclarMumentBinding::inflate) {

    private val viewModel: MumentDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        backBtnListener()
        checkBoxListener()
        nextBtnListener()
        clickListener()
    }

    private fun backBtnListener() {
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }

    private fun clickListener() {
        binding.clFirstReason.setOnClickListener {
            binding.clFirstReason.isSelected = !binding.clFirstReason.isSelected
        }

        binding.clSecondReason.setOnClickListener {
            binding.clSecondReason.isSelected = !binding.clSecondReason.isSelected
        }

        binding.clThirdReason.setOnClickListener {
            binding.clThirdReason.isSelected = !binding.clThirdReason.isSelected
        }

        binding.clForthReason.setOnClickListener {
            binding.clForthReason.isSelected = !binding.clForthReason.isSelected
        }

        binding.clFifthReason.setOnClickListener {
            binding.clFifthReason.isSelected = !binding.clFifthReason.isSelected
        }

        binding.clSixthReason.setOnClickListener {
            binding.clSixthReason.isSelected = !binding.clSixthReason.isSelected
        }

        binding.clSeventhReason.setOnClickListener {
            binding.clSeventhReason.isSelected = !binding.clSeventhReason.isSelected
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