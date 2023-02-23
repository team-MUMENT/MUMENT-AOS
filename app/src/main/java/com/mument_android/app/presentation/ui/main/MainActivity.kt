package com.mument_android.app.presentation.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.gson.Gson
import com.mument_android.R
import com.mument_android.app.presentation.RestrictUserDialog
import com.mument_android.app.presentation.ui.detail.mument.navigator.EditMumentNavigator
import com.mument_android.app.presentation.ui.detail.mument.navigator.checkCurrentFragment
import com.mument_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.core.util.Constants.TO_MUMENT_DETAIL
import com.mument_android.core.util.Constants.TO_MUSIC_DETAIL
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.util.ViewUtils.snackBar
import com.mument_android.databinding.ActivityMainBinding
import com.mument_android.detail.util.SuggestionNotifyAccessDialogFragment
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.home.main.HomeFragment
import com.mument_android.locker.LockerFragment
import com.mument_android.mypage.MyPageActivity
import com.mument_android.record.RecordActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    EditMumentNavigator {
    lateinit var navController: NavController
    val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
        binding.appBar.outlineAmbientShadowColor =
            ContextCompat.getColor(this, R.color.mument_bottom_navi_shadow_color)
        binding.appBar.outlineSpotShadowColor =
            ContextCompat.getColor(this, R.color.mument_bottom_navi_shadow_color)
        floatingBtnListener()
        customAppBar()
        isLimitUserNetwork()
        isRestrictUser()
        /*
        TODO 소식창에서 접근 시에 finish() 안하면 이거 그대로 써야함 이래도 홈까지는 가서 나가야 함
        */
        intent?.getStringExtra(MUSIC_INFO_ENTITY)?.let { music ->
            intent.getStringExtra(MUMENT_ID)?.let { mumentId ->
                val bundle = Bundle().also {
                    it.putString(MUMENT_ID, mumentId)
                    it.putString(MUSIC_INFO_ENTITY, music)
                }
                navController.navigate(R.id.action_homeFragment_to_mumentDetailFragment, bundle)
            } ?: navController.navigate(
                R.id.action_homeFragment_to_musicDetailFragment,
                Bundle().apply { putString(MUSIC_INFO_ENTITY, music) })
        }
    }

    private fun saveTestToken() {
        collectFlowWhenStarted(dataStoreManager.accessTokenFlow) {
            viewModel.saveTestAccessToken()
        }

        collectFlowWhenStarted(dataStoreManager.refreshTokenFlow) {
            viewModel.saveTestRefreshToken()
        }
    }


    //TODO : 아이콘 변경

    //appbar 상단 모서리 radius값 추가
    private fun customAppBar() {
        val radius = 48f
        val bottomAppBar = binding.appBar

        val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, radius)
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .build()
    }

    //floatingBtn 클릭 시 기록하기 뷰로 이동
    private fun floatingBtnListener() {
        binding.floatingActionButton.setOnClickListener {
            viewModel.limitUser.observe(this) {
                if (it.restricted == true) {
                    RestrictUserDialog(this).show(supportFragmentManager, "test")
                } else {
                    val intent = Intent(this, RecordActivity::class.java)
                    recordMumentLauncher.launch(intent)
                }
            }
        }
    }

    //EditMumentNavigatorProvider에서 사용
    val recordMumentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    moveMusicDetail(it)
                }
            }
        }

    private fun moveMusicDetail(intent: Intent) {
        if (intent.getStringExtra(TO_MUSIC_DETAIL) == TO_MUSIC_DETAIL) {
            intent.getBooleanExtra("COUNT", false).let {
                if (it && !NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                    SuggestionNotifyAccessDialogFragment.newInstance { result ->
                        if (result) {
                            moveToAlarmSetting()
                        }
                    }.show(supportFragmentManager, "Suggestion")
                } else {
                    snackBar(
                        binding.clSnackBar,
                        getString(com.mument_android.detail.R.string.record_finish_record)
                    )
                }
            }
            intent.getParcelableExtra<MusicInfoEntity>(MUSIC_INFO_ENTITY)?.let { musicInfo ->
                intent.getStringExtra(MUMENT_ID)?.let { mumentId ->
                    val bundle = Bundle().apply {
                        putParcelable(MUSIC_INFO_ENTITY, musicInfo)
                        putString(MUMENT_ID, mumentId)
                    }
                    navController.navigate(
                        R.id.musicDetailFragment,
                        bundle,
                        NavOptions.Builder().setPopUpTo(R.id.musicDetailFragment, false).build()
                    )
                }
            }
        } else if (intent.getStringExtra(TO_MUMENT_DETAIL) == TO_MUMENT_DETAIL) {
            intent.getParcelableExtra<MusicInfoEntity>(MUSIC_INFO_ENTITY)?.let { musicInfo ->
                intent.getStringExtra(MUMENT_ID)?.let { mumentId ->
                    snackBar(
                        binding.clSnackBar,
                        getString(com.mument_android.detail.R.string.modify_record)
                    )
                    val bundle = Bundle().apply {
                        putString(MUSIC_INFO_ENTITY, Gson().toJson(musicInfo))
                        putString(MUMENT_ID, mumentId)
                    }
                    navController.navigate(
                        R.id.mumentDetailFragment,
                        bundle,
                        NavOptions.Builder().setPopUpTo(R.id.mumentDetailFragment, true).build()
                    )
                }
            }
        }
    }

    private fun isLimitUserNetwork() {
        viewModel.limitUser()
    }

    private fun initNavigation() {
        binding.navBar.background = null
        binding.navBar.menu.getItem(1).isEnabled = false
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.navBar, navController)

        binding.navBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    if (checkCurrentFragment() !is HomeFragment) {
                        changeCurrentFragment(R.id.homeFragment)
                    }
                }
                R.id.lockerFragment -> {
                    if (checkCurrentFragment() !is LockerFragment) {
                        changeCurrentFragment(R.id.lockerFragment)
                    }
                }
            }
            false
        }
    }

    private fun moveToAlarmSetting() {
        Intent().apply {
            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //버전에 따른 알림 설정
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            } else {
                putExtra("app_package", packageName)
                putExtra("app_uid", applicationInfo.uid)
            }
        }.also { destination ->
            startActivity(destination)
        }
    }

    private fun changeCurrentFragment(destinationId: Int) {
        navController.navigate(
            destinationId,
            null,
            NavOptions.Builder().setPopUpTo(destinationId, true).build()
        )
    }

    private fun isRestrictUser() {
        viewModel.limitUser.observe(this) {
            if (it.restricted == true) {
                RestrictUserDialog(this).show(supportFragmentManager, "test")
            }
        }
    }

    override fun editMument(mumentId: String, mumentDetailEntity: MumentDetailEntity) {
        viewModel.changeMumentId(mumentId)
        viewModel.changeMumentContents(mumentDetailEntity)
    }

    override fun musicMument(musicId: String) {
        viewModel.changeMusicId(musicId)
    }

    override fun recordMusic(music: Music) {
        viewModel.changeMusic(music)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e("NEW INTENT", intent.toString())
        intent?.getStringExtra(MUSIC_INFO_ENTITY)?.let { music ->
            intent.getStringExtra(MUMENT_ID)?.let { mumentId ->
                val bundle = Bundle().also {
                    it.putString(MUMENT_ID, mumentId)
                    it.putString(MUSIC_INFO_ENTITY, music)
                }
                navController.navigate(R.id.action_homeFragment_to_mumentDetailFragment, bundle)
            } ?: navController.navigate(
                R.id.action_homeFragment_to_musicDetailFragment,
                Bundle().apply { putString(MUSIC_INFO_ENTITY, music) })
        }
    }
}