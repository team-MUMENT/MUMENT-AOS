package com.mument_android.login


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.angdroid.navigation.MainHomeNavigatorProvider
import com.angdroid.navigation.MypageNavigatorProvider
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.util.FirebaseAnalyticsUtil
import com.mument_android.login.databinding.ActivityProfileSettingBinding
import com.mument_android.login.util.CustomSnackBar
import com.mument_android.login.util.GalleryUtil
import com.mument_android.login.util.shortToast
import com.mument_android.login.util.toByteArray
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.util.regex.Pattern
import javax.inject.Inject


@AndroidEntryPoint
class ProfileSettingActivity :
    BaseActivity<ActivityProfileSettingBinding>(inflate = ActivityProfileSettingBinding::inflate) {
    private lateinit var intentLauncher: ActivityResultLauncher<Intent>
    private lateinit var inputMethodManager: InputMethodManager

    @Inject
    lateinit var mainHomeNavigatorProvider: MainHomeNavigatorProvider

    @Inject
    lateinit var mypageNavigatorProvider: MypageNavigatorProvider

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
        getUserInfo()
        scrollToBottom()
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
            if (!Pattern.matches("^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9\\s]{2,15}\$", it) || it.trim().length < 2) {
                viewModel.isRightPattern.value = false
                binding.tvPattern.isSelected = true
            } else if (it.isEmpty() || Pattern.matches(
                    "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9\\s]{2,15}\$",
                    it
                ) || it.trim().length >= 2
            ) {
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
                binding.ivProfile.setImageResource(R.drawable.mument_profile_default_image)
            } else {
                binding.clSelectImg.visibility = View.VISIBLE
                binding.tvSelectLibrary.setOnClickListener {
                    uploadPhotoClickListener(galleryUtil)
                    binding.clSelectImg.visibility = View.GONE
                }
                binding.tvDeleteProfile.setOnClickListener {
                    binding.ivProfile.setImageResource(R.drawable.mument_profile_default_image)
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
                        placeholder(R.drawable.mument_profile_default_image)
                        transformations(CircleCropTransformation())
                    }
                    if (imageUri != null) {
                        viewModel.imageUri.value = uri
                    }
                }

                val isProfile = intent.getBooleanExtra("isSignUp", true)
                if (!isProfile) {
                    //회원가입 시 이미지 설정 여부 GA
                    FirebaseAnalyticsUtil.firebaseLog(
                        "signup_process",
                        "journey",
                        "signup_profile_img"
                    )
                }
            }
        }
    }

    //뒤로가기 클릭 리스너
    private fun backBtnListener() {
        binding.ivProfileBack.setOnClickListener {
            backBtnEvent()
        }
    }

    //뒤로가기
    private fun backBtnEvent() {
        val isCheckMypage = intent.getIntExtra("checkMyPage", 0)
        if (isCheckMypage == 0) {
            val intent = Intent(this, LogInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        finish()
    }


    private fun nickNameDupNetwork() {
        val nickname = binding.etNickname.text.toString().trim()
        viewModel.nickNameDupCheck(nickname)
    }

    private fun putProfileNetwork() {
        val nickname = binding.etNickname.text.trim().toString()
        viewModel.imageUri.value?.let {
            binding.ivProfile.drawable.toBitmapOrNull(720, 720, Bitmap.Config.ARGB_8888)?.let { bitmap ->
                Log.e("BITMAP", it.toString())
                viewModel.putProfile(bitmap.toByteArray(Bitmap.CompressFormat.PNG, 100), "userName", nickname)
            }
        } ?: changeImageUri(
            when ((0..2).random()) {
                0 -> R.drawable.mument_profile_love_45
                1 -> R.drawable.mument_profile_smile_45
                else -> R.drawable.mument_profile_sleep_45
            }, nickname
        )
    }

    private fun changeImageUri(img: Int, nickName: String) {
        val bitmap = BitmapFactory.decodeResource(resources, img)
        viewModel.putProfile(bitmap.toByteArray(Bitmap.CompressFormat.PNG, 100), "userName", nickName)
    }

    private suspend fun nickNameDupCheck() {
        delay(100)
        viewModel.isDuplicate.observe(this) {
            if (intent.getStringExtra("nickname") == binding.etNickname.text.toString()) {
                putProfileNetwork()
            } else if (it == 200) {
                CustomSnackBar.make(binding.root.rootView, "중복된 닉네임이 존재합니다.").show()
            } else if (it == 204) {
                putProfileNetwork()
            } else {
            }
        }
    }

    private fun dulCheckListener() {
        val isCheckMypage = intent.getIntExtra("checkMyPage", 0)
        collectFlowWhenStarted(viewModel.isSuccess) { success ->
            if (success) {
                if (isCheckMypage == 1)
                    finish()
                else {
                    val isProfile = intent.getBooleanExtra("isSignUp", true)
                    if (!isProfile) {
                        // 회원가입 성공 GA
                        FirebaseAnalyticsUtil.firebaseLog(
                            "signup_process",
                            "journey",
                            "signup_success"
                        )
                    }
                    moveToMainActivity()
                }
            }
        }

        binding.tvProfileFinish.setOnClickListener {
            lifecycleScope.launch {
                nickNameDupNetwork()
                nickNameDupCheck()
            }
            val isProfile = intent.getBooleanExtra("isSignUp", true)
            if (!isProfile) {
                //회원가입 시 닉네임 중복 여부 GA
                FirebaseAnalyticsUtil.firebaseLog(
                    "signup_process",
                    "journey",
                    "signup_duplication_test"
                )
            }
        }
    }

    private fun getUserInfo() {

        val nickname = intent.getStringExtra("nickname")
        nickname?.let {
            viewModel.mumentNickName.value = nickname
        }

        val img = intent.getStringExtra("img")
        img?.let {
            viewModel.imageUri.value = Uri.parse(it)
            Log.e("IMG", viewModel.imageUri.value.toString())
        }

        if (viewModel.mumentNickName.value == "null" && viewModel.imageUri.value == null) {
            viewModel.mumentNickName.value = ""
            viewModel.imageUri.value = null
        } else {
            binding.etNickname.setText(viewModel.mumentNickName.value)
            binding.ivProfile.load(viewModel.imageUri.value) {
                crossfade(true)
                placeholder(R.drawable.mument_profile_default_image)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun scrollToBottom() {
        binding.svProfileScroll.viewTreeObserver.addOnGlobalLayoutListener {
            val rec = Rect()
            binding.svProfileScroll.getWindowVisibleDisplayFrame(rec)
            val screenHeight = binding.svProfileScroll.rootView.height
            val keypadHeight = screenHeight - rec.bottom

            if (keypadHeight > screenHeight * 0.15) {
                binding.svProfileScroll.fullScroll(ScrollView.FOCUS_DOWN)
            }
        }
    }

    private fun moveToMainActivity() {
        mainHomeNavigatorProvider.profileSettingToMain()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backBtnEvent()
    }
}