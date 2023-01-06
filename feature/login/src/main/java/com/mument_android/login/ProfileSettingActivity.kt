package com.mument_android.login

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.CircleCropTransformation

import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.login.databinding.ActivityProfileSettingBinding
import java.util.regex.Pattern

class ProfileSettingActivity :
    BaseActivity<ActivityProfileSettingBinding>(R.layout.activity_profile_setting) {

    private val viewModel: LogInViewModel by viewModels()
    private val finalNickname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.isImageSelected.value = false

    }

    override fun onResume() {
        super.onResume()
        isSpace()
        cancelBtnListener()
        isActiveBtn()
        deleteText()
        imageClickListener()
    }

    private fun deleteText() {
        binding.ivDelete.setOnClickListener {
            binding.etNickname.setText("")
        }
    }

    private fun imageClickListener() {
        binding.ivProfile.setOnClickListener {
            isImageExist()
        }
    }

    private fun isSpace() {
        viewModel.mumentNickName.observe(this) {
            if (!Pattern.matches("^[ㄱ-ㅎ가-힣a-zA-Z0-9\\s]{2,15}\$", it)) {
                viewModel.isRightPattern.value = false
                binding.tvPattern.isSelected = true
            } else if (it == "" || Pattern.matches("^[ㄱ-ㅎ가-힣a-zA-Z0-9\\s]{2,15}\$", it)) {
                viewModel.isRightPattern.value = true
                binding.tvPattern.isSelected = false
            }

            viewModel.isActive.value = it.trim().length >= 2 && !binding.tvPattern.isSelected

        }
    }

    private fun isActiveBtn() {
        viewModel.isActive.observe(this) {
            binding.tvProfileFinish.isSelected = it
        }
    }


    private fun isImageExist() {
        if (viewModel.isImageSelected.value == false) {
            aboutPermission()
        } else {
            binding.clSelectImg.visibility = View.VISIBLE
            binding.tvSelectLibrary.setOnClickListener {
                aboutPermission()
                binding.clSelectImg.visibility = View.GONE
            }
            binding.tvDeleteProfile.setOnClickListener {
                binding.ivProfile.setImageResource(R.drawable.circle_fill_default)
                viewModel.isImageSelected.value = false
                binding.clSelectImg.visibility = View.GONE
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                selectImage()
            }
        }

    private fun cancelBtnListener() {
        binding.tvProfileCancel.setOnClickListener {
            binding.clSelectImg.visibility = View.GONE
        }

        binding.clSelectImg.setOnClickListener {
            binding.clSelectImg.visibility = View.GONE
        }
    }


    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            binding.ivProfile.load(uri) {
                crossfade(true)
                placeholder(R.drawable.circle_fill_default)
                transformations(CircleCropTransformation())
            }
        }


    private fun aboutPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            selectImage()
        } else if (ContextCompat.checkSelfPermission(
                this,
                READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {

            requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
        }

    }


    private fun selectImage() {
        getContent.launch("image/*")
    }


}
