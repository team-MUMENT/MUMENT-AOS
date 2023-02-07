package com.mument_android.detail.mument.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.detail.R
import com.mument_android.detail.databinding.ActivityDeclarMumentBinding
import com.mument_android.detail.mument.contract.MumentDetailContract
import com.mument_android.detail.mument.viewmodel.MumentDetailViewModel
import com.mument_android.detail.mument.viewmodel.MumentReportViewModel
import com.mument_android.domain.entity.detail.ReportRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeclarMumentActivity :
    BaseActivity<ActivityDeclarMumentBinding>(inflate = ActivityDeclarMumentBinding::inflate) {

    private val viewModel: MumentReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("MUMENT_ID")?.let {
            Log.e("MUMENT_ID", it)
            viewModel.mumentId.value = it
        }
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
            if(binding.ivBlockCheck.isSelected) {
                val mumentId = viewModel.mumentId.value ?: ""
                viewModel.blockUser(mumentId)
                reportNetwork()
                showToast("신고 및 차단이 완료되었습니다.")

            } else {
                reportNetwork()
                showToast("신고가 접수되었습니다.")

            }
            finish()

        }
    }

    private fun reportNetwork() {
        val reasonList : MutableList<Int> = mutableListOf()
        //TODO : 신고 api연결
        if(binding.ivFirstReason.isSelected) {
            reasonList.add(1)
        }
        if(binding.ivSecondReason.isSelected) {
            reasonList.add(2)
        }
        if(binding.ivThirdReason.isSelected) {
            reasonList.add(3)
        }
        if(binding.ivForthReason.isSelected) {
            reasonList.add(4)
        }
        if(binding.ivFifthReason.isSelected) {
            reasonList.add(5)
        }
        if(binding.ivSixthReason.isSelected) {
            reasonList.add(6)
        }
        if(binding.ivSeventhReason.isSelected) {
            reasonList.add(7)
        }
        Log.e("TEST123", "${reasonList.toString()}")
        val mumentId = viewModel.mumentId.value ?: ""
        val ReportRequest = ReportRequest(binding.editText.text.toString(),reasonList)
        viewModel.reportMument( mumentId,ReportRequest)

    }

}