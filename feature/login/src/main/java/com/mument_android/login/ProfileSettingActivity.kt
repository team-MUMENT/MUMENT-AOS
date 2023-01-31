package com.mument_android.login

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.snackbar.Snackbar
import com.mument_android.core.network.ApiResult
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.launchWhenCreated
import com.mument_android.login.databinding.ActivityProfileSettingBinding
import com.mument_android.login.util.CustomSnackBar
import com.mument_android.login.util.GalleryUtil
import com.mument_android.login.util.shortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import okhttp3.Response
import java.util.regex.Pattern


@AndroidEntryPoint
class ProfileSettingActivity :
    BaseActivity<ActivityProfileSettingBinding>(inflate = ActivityProfileSettingBinding::inflate) {
    private lateinit var intentLauncher: ActivityResultLauncher<Intent>
    private lateinit var inputMethodManager: InputMethodManager

    private val viewModel: LogInViewModel by viewModels()

    //이미지 접근 권한여부 설정
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                uploadImageListener()
            } else
                shortToast("권한요청이 거절되었습니다.")
        }

    private val galleryUtil = GalleryUtil(this, requestPermissionLauncher)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        isRightPattern()
        cancelBtnListener()
        isActiveBtn()
        deleteText()
        uploadImageCallbackListener()
        isImageExist()
        backBtnListener()
        dulCheckListener()
    }

    //edittext에 작성한 텍스트 삭제 버튼 클릭 리스너
    private fun deleteText() {
        binding.ivDelete.setOnClickListener {
            binding.etNickname.setText("")
        }
    }

    //닉네임 정규식 확인
    private fun isRightPattern() {
        viewModel.mumentNickName.observe(this) {
            if (!Pattern.matches("^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9\\s]{2,15}\$", it)) {
                viewModel.isRightPattern.value = false
                binding.tvPattern.isSelected = true
            } else if (it == "" || Pattern.matches("^[ㄱ-ㅎ가-힣a-zA-Z0-9\\s]{2,15}\$", it)) {
                viewModel.isRightPattern.value = true
                binding.tvPattern.isSelected = false
            }

            viewModel.isActive.value = it.trim().length >= 2 && !binding.tvPattern.isSelected

        }
    }

    //버튼 활성화
    private fun isActiveBtn() {
        viewModel.isActive.observe(this) {
            binding.tvProfileFinish.isSelected = it
        }
    }

    //이미지 여부 확인 후 없다면 => 갤러리로 바로 이동, 있다면 -> 삭제 or 변경 여부 선택
    private fun isImageExist() {
        binding.ivProfile.setOnClickListener {
            if (viewModel.imageUri.value == null) {
                uploadPhotoClickListener(galleryUtil)
            } else {
                binding.clSelectImg.visibility = View.VISIBLE
                binding.tvSelectLibrary.setOnClickListener {
                    uploadPhotoClickListener(galleryUtil)
                    binding.clSelectImg.visibility = View.GONE
                }
                binding.tvDeleteProfile.setOnClickListener {
                    binding.ivProfile.setImageResource(R.drawable.circle_fill_default)
                    viewModel.imageUri.value = null
                    binding.clSelectImg.visibility = View.GONE
                }
            }
        }
    }

    //취소 버튼 클릭 리스너
    private fun cancelBtnListener() {
        binding.tvProfileCancel.setOnClickListener {
            binding.clSelectImg.visibility = View.GONE
        }

        binding.clSelectImg.setOnClickListener {
            binding.clSelectImg.visibility = View.GONE
        }
    }

    private fun uploadImageListener() {
        Intent(Intent.ACTION_PICK).apply {
            type = MediaStore.Images.Media.CONTENT_TYPE
            data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
            setResult(RESULT_OK)
            intentLauncher.launch(this)
        }
    }

    //갤러리 접근 권한 있다면 갤러리로 이동
    private fun uploadPhotoClickListener(galleryUtil: GalleryUtil) {
        if (galleryUtil.aboutPermission()) {
            uploadImageListener()
        }
    }

    //이미지 선택 시, 적용
    private fun uploadImageCallbackListener() {
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        intentLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                it.data?.data?.let { uri ->
                    val imageUri = it.data?.data
                    binding.ivProfile.load(uri) {
                        viewModel.imageUri.value = uri
                        crossfade(true)
                        placeholder(R.drawable.circle_fill_default)
                        transformations(CircleCropTransformation())
                    }
                    if (imageUri != null) {
                        viewModel.imageUri.value = uri
                    }
                }
            }
        }
    }

    //뒤로가기 클릭 리스너
    private fun backBtnListener() {
        binding.ivProfileBack.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }
    }

    private fun nickNameDupNetwork() {
        val nickname = binding.etNickname.text.toString()
        viewModel.nickNameDupCheck(nickname)
    }

    private suspend fun nickNameDupCheck() {
        delay(2500)
        viewModel.isDuplicate.observe(this) {
            if(viewModel.isDuplicate.value?.toInt() == 200) {
                CustomSnackBar.make(binding.root.rootView, "중복된 닉네임이 존재합니다.").show()
            } else if (viewModel.isDuplicate.value?.toInt() == 204) {
                //TODO 프로필 서버통신
                finish()
            } else {
                Log.e("닉네임 중복확인","서버통신 실패")
            }
        }
    }

    private fun dulCheckListener() {
        binding.tvProfileFinish.setOnClickListener {
            viewModel.viewModelScope.launch {
                nickNameDupNetwork()
                nickNameDupCheck()
            }
        }
    }
}
