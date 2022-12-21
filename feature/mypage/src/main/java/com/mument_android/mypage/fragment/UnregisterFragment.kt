package com.mument_android.mypage.fragment

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.mypage.MyPageViewModel
import com.mument_android.mypage.R
import com.mument_android.mypage.databinding.FragmentUnregisterBinding

class UnregisterFragment : Fragment() {

    private var binding by AutoClearedValue<FragmentUnregisterBinding>()
    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUnregisterBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.myPageViewModel = myPageViewModel

        reasonBtnEvent()
        reasonChooseBtnEvent()
    }

    //이유 선택 박스 눌렀을 때
    private fun reasonChooseBtnEvent() {
        binding.clReason.setOnClickListener {
            binding.tvChooseReason.let {
                it.setText(R.string.unregister_choose_reason)
                it.setTextColor(Color.GRAY)
            }
            myPageViewModel.clickReasonChoose()
            myPageViewModel.isSelectSixthReason.value = false
        }
    }

    // 이유 선택 했을 때
    private fun reasonBtnEvent() {
        binding.rgChooseReason.setOnCheckedChangeListener { _, checkedID ->

            myPageViewModel.clickReasonChoose()
            binding.tvChooseReason.setTextColor(Color.BLACK)

            when (checkedID) {
                R.id.unregister_reason_first -> {
                    binding.tvChooseReason.setText(R.string.unregister_reason_first)
                    myPageViewModel.isSelectSixthReason.value = false
                }
                R.id.unregister_reason_second -> {
                    binding.tvChooseReason.setText(R.string.unregister_reason_second)
                    myPageViewModel.isSelectSixthReason.value = false
                }
                R.id.unregister_reason_third -> {
                    binding.tvChooseReason.setText(R.string.unregister_reason_third)
                    myPageViewModel.isSelectSixthReason.value = false
                }
                R.id.unregister_reason_fourth -> {
                    binding.tvChooseReason.setText(R.string.unregister_reason_fourth)
                    myPageViewModel.isSelectSixthReason.value = false
                }
                R.id.unregister_reason_fifth -> {
                    binding.tvChooseReason.setText(R.string.unregister_reason_fifth)
                    myPageViewModel.isSelectSixthReason.value = false
                }
                R.id.unregister_reason_sixth -> {
                    binding.tvChooseReason.setText(R.string.unregister_reason_sixth)
                    myPageViewModel.isSelectSixthReason.value = true
                }
            }
        }
    }


}