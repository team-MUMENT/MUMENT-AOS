package com.mument_android.mypage.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Transformation
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.login.LogInActivity
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

        isAgreeBtnEvent()
        unregisterFinish()
    }


    //이유 선택 박스 눌렀을 때
    private fun reasonChooseBtnEvent() {
        binding.clReason.setOnClickListener {
            binding.tvChooseReason.let {
                it.setText(R.string.unregister_choose_reason)
                it.setTextColor(Color.GRAY)
            }
            binding.clReason.isSelected = !binding.clReason.isSelected
            myPageViewModel.clickReasonChooseBox()
            setAnimationReason()
            myPageViewModel.isSelectSixthReason.value = false
        }
    }

    // 이유 선택 했을 때
    private fun reasonBtnEvent() {
        binding.rgChooseReason.setOnCheckedChangeListener { _, checkedID ->
            myPageViewModel.clickReasonChooseBox()
            myPageViewModel.clickReasonChoose()
            setAnimationReason()
            binding.tvChooseReason.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.mument_color_black1
                )
            )
            binding.clReason.isSelected = false

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

    //이유 선택 박스 눌렀을 때 애니메이션 적용
    private fun setAnimationReason() {
        val animUpToDown = AnimationUtils.loadAnimation(requireContext(),R.anim.move_up_to_down)
        if (myPageViewModel.isClickReasonChooseBox.value == true)
            binding.rgChooseReason.startAnimation(animUpToDown)
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
            //TODO  서버연결하기
            val intent = Intent(activity, LogInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun expandAction(view: View) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val actualHeight = view.measuredHeight

        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime >= 0.5F) {
                    view.visibility = View.VISIBLE
                } else view.layoutParams.height =
                    (actualHeight + (actualHeight * interpolatedTime)).toInt()
                view.requestLayout()
            }
        }
        animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
    }

    private fun collapse(view: View) {
        val actualHeight = view.measuredHeight
        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime >= 0.99F) {
                    view.visibility = View.GONE
                } else {
                    view.layoutParams.height =
                        (actualHeight - (actualHeight * interpolatedTime)).toInt()
                    view.requestLayout()
                }
            }
        }
        animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
    }
}

