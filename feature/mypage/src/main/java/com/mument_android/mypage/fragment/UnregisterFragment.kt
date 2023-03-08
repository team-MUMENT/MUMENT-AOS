package com.mument_android.mypage.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.core_dependent.util.ViewUtils.hideKeyboard
import com.mument_android.login.LogInActivity
import com.mument_android.mypage.MyPageViewModel
import com.mument_android.mypage.R
import com.mument_android.mypage.databinding.FragmentUnregisterBinding
import com.mument_android.mypage.util.UnregisterReason
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UnregisterFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentUnregisterBinding>()
    private val myPageViewModel: MyPageViewModel by viewModels()

    @Inject
    lateinit var dataStoreManager: DataStoreManager

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

        reasonChooseTouchEvent()

        settingUserName()
        isAgreeBtnEvent()
        unregisterFinish()

        backBtnListener()

    }

    private fun settingUserName() {
        myPageViewModel.userInfo()
    }


    //이유 선택 박스 눌렀을 때
    private fun reasonChooseBtnEvent() {
        binding.clReason.setOnClickListener {
            if (binding.rgChooseReason.isEmpty()) {
                binding.tvChooseReason.let {
                    it.setText(R.string.unregister_choose_reason)
                    it.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.mument_color_gray1
                        )
                    )
                }
            }
            binding.ivChooseReason.isSelected = !binding.ivChooseReason.isSelected
            myPageViewModel.clickReasonChooseBox()
            setAnimationReason()
            view?.hideKeyboard()
        }
    }

    // 이유 선택 했을 때
    private fun reasonBtnEvent() {
        binding.rgChooseReason.setOnCheckedChangeListener { _, checkedID ->
            myPageViewModel.clickReasonChooseBox()
            myPageViewModel.clickReasonChoose()

            //이유 선택했을 때 이유선택박스 selector
            binding.ivChooseReason.isSelected = false


            myPageViewModel.clickSixthReason(checkedID == R.id.unregister_reason_sixth)

            when (checkedID) {
                R.id.unregister_reason_first -> {
                    binding.tvChooseReason.text = getString(R.string.unregister_reason_first)
                    myPageViewModel.getUnregisterReasonIndex(2)
                }
                R.id.unregister_reason_second -> {
                    binding.tvChooseReason.text = getString(R.string.unregister_reason_second)
                    myPageViewModel.getUnregisterReasonIndex(3)
                }
                R.id.unregister_reason_third -> {
                    binding.tvChooseReason.text = getString(R.string.unregister_reason_third)
                    myPageViewModel.getUnregisterReasonIndex(4)
                }
                R.id.unregister_reason_fourth -> {
                    binding.tvChooseReason.text = getString(R.string.unregister_reason_fourth)
                    myPageViewModel.getUnregisterReasonIndex(5)
                }
                R.id.unregister_reason_fifth -> {
                    binding.tvChooseReason.text = getString(R.string.unregister_reason_fifth)
                    myPageViewModel.getUnregisterReasonIndex(6)
                }
                R.id.unregister_reason_sixth -> {
                    binding.tvChooseReason.text = getString(R.string.unregister_reason_sixth)
                    myPageViewModel.getUnregisterReasonIndex(7)
                }
                else -> {
                    ""
                }
            }
        }
    }

    //이유 선택 박스 눌렀을 때 애니메이션 적용
    private fun setAnimationReason() {
        val animUpToDown = AnimationUtils.loadAnimation(requireContext(), R.anim.move_up_to_down)
        if (myPageViewModel.isClickReasonChooseBox.value == true)
            binding.rgChooseReason.startAnimation(animUpToDown)
    }

    //이유 선택 외의 영역을 터치했을 때
    private fun reasonChooseTouchEvent() {
        binding.clUnregister.setOnClickListener {
            myPageViewModel.initReasonChooseBox()
            binding.ivChooseReason.isSelected = false
            view?.hideKeyboard()
        }
    }


    //동의 버튼 눌렀을 때
    private fun isAgreeBtnEvent() {
        binding.btnUnregisterAgree.setOnClickListener {
            myPageViewModel.clickUnregisterAgree()
            binding.btnUnregisterAgree.isSelected = !binding.btnUnregisterAgree.isSelected
        }
    }

    //회원탈퇴 버튼 눌렀을 때
    private fun unregisterFinish() {
        binding.btnUnregisterFinish.setOnClickListener {
            lifecycleScope.launch {
                myPageViewModel.postUnregisterReason(dataStoreManager.kakaoTokenFlow.firstOrNull() ?: "")

            }
        }
        myPageViewModel.isUnregisterSuccess.observe(viewLifecycleOwner) {
            if (it) {
                myPageViewModel.deleteInfo()
                requireActivity().finish()
                startActivity(Intent(requireActivity(), LogInActivity::class.java))
            } else {
                Log.e("unregisterFinish()", "회원탈퇴 실패")
            }
        }
    }

    private fun backBtnListener() {
        binding.btnUnregisterBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}

