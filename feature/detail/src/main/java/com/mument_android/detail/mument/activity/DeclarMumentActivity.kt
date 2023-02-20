package com.mument_android.detail.mument.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ui.MumentDialogBuilder
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

    private val reportViewModel: MumentReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("MUMENT_ID")?.let {
            Log.e("MUMENT_ID", it)
            reportViewModel.mumentId.value = it
        }
        binding.viewModel = reportViewModel
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

    private fun clickListener() = with(binding) {

        clFirstReason.setOnClickListener {
            clFirstReason.isSelected = !clFirstReason.isSelected
            isBtnActive()
        }

        clSecondReason.setOnClickListener {
            clSecondReason.isSelected = !clSecondReason.isSelected
            isBtnActive()
        }

        clThirdReason.setOnClickListener {
            clThirdReason.isSelected = !clThirdReason.isSelected
            isBtnActive()
        }

        clForthReason.setOnClickListener {
            clForthReason.isSelected = !clForthReason.isSelected
            isBtnActive()
        }

        clFifthReason.setOnClickListener {
            clFifthReason.isSelected = !clFifthReason.isSelected
            isBtnActive()
        }

        clSixthReason.setOnClickListener {
            clSixthReason.isSelected = !clSixthReason.isSelected
            isBtnActive()

        }

        clSeventhReason.setOnClickListener {
            clSeventhReason.isSelected = !clSeventhReason.isSelected
            isBtnActive()
            observingData()
        }
    }

    private fun observingData() {
        reportViewModel.reasonLength.observe(this) {
            binding.apply {
                tvNotifyFinish.isEnabled =
                    (clFirstReason.isSelected || clSecondReason.isSelected || clThirdReason.isSelected || clForthReason.isSelected || clFifthReason.isSelected || clSixthReason.isSelected || (clSeventhReason.isSelected) && it.isNotEmpty())
            }
        }
    }

    private fun isBtnActive() {
        binding.apply {
            val btnList = listOf(
                clFirstReason.isSelected,
                clSecondReason.isSelected,
                clThirdReason.isSelected,
                clForthReason.isSelected,
                clFifthReason.isSelected,
                clSixthReason.isSelected
            )
            if (clSeventhReason.isSelected) {
                tvNotifyFinish.isEnabled = reportViewModel.reasonLength.value?.isNotBlank() == true
            } else tvNotifyFinish.isEnabled = btnList.any { it }
        }
    }


    private fun checkBoxListener() {
        binding.clBlock.setOnClickListener {
            binding.ivBlockCheck.isSelected = !binding.ivBlockCheck.isSelected
        }
    }


    private fun nextBtnListener() {
        binding.tvNotifyFinish.setOnClickListener {
            if (binding.ivBlockCheck.isSelected) {
                reportNetwork()
                val mumentId = reportViewModel.mumentId.value ?: ""
                reportViewModel.isReportMuemnt.observe(this) { isReportMument ->
                    if (isReportMument == true) {
                        reportViewModel.blockUser(mumentId)
                        reportViewModel.error.observe(this) {
                            if (it == null) {
                                showToast("신고 및 차단이 완료되었습니다.")

                            } else {

                                MumentDialogBuilder()
                                    .setBody("신고가 정상적으로 완료되었으나, 일시적인 네트워크 오류로 인해 차단을 실패했습니다. 잠시 후 다시 시도해주시기 바랍니다.")
                                    .setAllowListener("확인") {
                                        finish()
                                    }
                                    .build()
                                    .show(supportFragmentManager, attributionTag)
                               // showToast(it)
                            }.also {

                            }

                        }
                    }
                }
            } else {
                reportNetwork()
                reportViewModel.isReportMuemnt.observe(this) { isReportMument ->
                    if (isReportMument == true) {
                        showToast("신고가 접수되었습니다.")
                        finish()
                    }
                }
            }

        }
    }

    private fun reportNetwork() {
        val reasonList: MutableList<Int> = mutableListOf()
        if (binding.ivFirstReason.isSelected) {
            reasonList.add(1)
        }
        if (binding.ivSecondReason.isSelected) {
            reasonList.add(2)
        }
        if (binding.ivThirdReason.isSelected) {
            reasonList.add(3)
        }
        if (binding.ivForthReason.isSelected) {
            reasonList.add(4)
        }
        if (binding.ivFifthReason.isSelected) {
            reasonList.add(5)
        }
        if (binding.ivSixthReason.isSelected) {
            reasonList.add(6)
        }
        if (binding.ivSeventhReason.isSelected) {
            reasonList.add(7)
        }
        val mumentId = reportViewModel.mumentId.value ?: ""
        val ReportRequest = ReportRequest(binding.editText.text.toString(), reasonList)
        reportViewModel.reportMument(mumentId, ReportRequest)
    }

}