package com.mument_android.login

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.system.Os.remove
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
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
        isRightPattern()
        isSpace()
        initImageViewProfile()
        cancelBtnListener()

    }

    private fun isRightPattern() {
        val nickname = viewModel.mumentNickName
        if (!Pattern.matches("^[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,15}\$", nickname.toString())) {
            viewModel.isRightPattern.value = false
            binding.tvPattern.isSelected = true
        } else {
            viewModel.isRightPattern.value = true
            binding.tvPattern.isSelected = false
        }
    }

    private fun isSpace() {
        binding.etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val str = p0?.trim().toString()
                viewModel.isActive.value = str.length >= 2
            }

        })
    }

    private fun initImageViewProfile() {
        binding.ivProfile.setOnClickListener {
            when {
                // 갤러리 접근 권한이 있는 경우
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                -> {
                    isImageExist()
                }

                // 갤러리 접근 권한이 없는 경우 & 교육용 팝업을 보여줘야 하는 경우
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                -> {
                    showPermissionContextPopup()
                }

                // 권한 요청 하기(requestPermissions) -> 갤러리 접근(onRequestPermissionResult)
                else -> requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )
            }

        }
    }

    // 권한 요청 승인 이후 실행되는 함수
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    navigateGallery()
                else
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                //
            }
        }
    }


    private fun isImageExist() {
        if (viewModel.isImageSelected.value == false) {
            navigateGallery()
        } else {
            binding.clSelectImg.visibility = View.VISIBLE
            binding.tvSelectLibrary.setOnClickListener {
                navigateGallery()
                binding.clSelectImg.visibility = View.GONE
            }
            binding.tvDeleteProfile.setOnClickListener {
                binding.ivProfile.setImageResource(R.drawable.circle_fill_default)
                viewModel.isImageSelected.value = false
                binding.clSelectImg.visibility = View.GONE
            }
        }
    }


    private fun navigateGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        // 가져올 컨텐츠들 중에서 Image 만을 가져온다.
        intent.type = "image/*"
        // 갤러리에서 이미지를 선택한 후, 프로필 이미지뷰를 수정하기 위해 갤러리에서 수행한 값을 받아오는 startActivityForeResult를 사용한다.
        startActivityForResult(intent, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 예외처리
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode) {
            // 2000: 이미지 컨텐츠를 가져오는 액티비티를 수행한 후 실행되는 Activity 일 때만 수행하기 위해서
            2000 -> {
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    binding.ivProfile.load(selectedImageUri) {
                        transformations(
                            RoundedCornersTransformation(150F)
                        )
                        build()
                    }
                    viewModel.isImageSelected.value = true

                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("프로필 이미지를 바꾸기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }

    private fun cancelBtnListener() {
        binding.tvProfileCancel.setOnClickListener {
            binding.clSelectImg.visibility = View.GONE
        }

        binding.clSelectImg.setOnClickListener {
            binding.clSelectImg.visibility = View.GONE
        }
    }


}