package com.mument_android.app.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.mument_android.R
import com.mument_android.app.presentation.ui.detail.mument.navigator.EditMumentNavigator
import com.mument_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.app.presentation.RestrictUserDialog
import com.mument_android.databinding.ActivityMainBinding
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.record.RecordActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    EditMumentNavigator {
    lateinit var navController: NavController
    val viewModel: MainViewModel by viewModels()
    @Inject lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigation()
        floatingBtnListener()
        customAppBar()
        isLimitUserNetwork()
        isRestrictUser()
    }

    private fun saveTestToken() {
        collectFlowWhenStarted(dataStoreManager.accessTokenFlow) {
            if (it.isNullOrEmpty()) viewModel.saveTestAccessToken()
        }

        collectFlowWhenStarted(dataStoreManager.refreshTokenFlow) {
            if (it.isNullOrEmpty()) viewModel.saveTestRefreshToken()
        }
        collectFlowWhenStarted(dataStoreManager.userIdFlow) {
            if (it.isNullOrEmpty()) viewModel.saveTestUserId()
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
                if(it.restricted == true) {
                    RestrictUserDialog(this).show(supportFragmentManager, "test")
                } else {
                    val intent = Intent(this, RecordActivity::class.java)
                    startActivity(intent)
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
        binding.navBar.setupWithNavController(navController)
        binding.navBar.setOnItemSelectedListener { item ->
            val bundle = Bundle()
            when (item.itemId) {
                R.id.fragment_home -> {
                    if (viewModel.checkHasMusic()) {
                        bundle.putString(MUSIC_ID, viewModel.musicId.value)
                        navController.navigate(
                            R.id.action_homeFragment_to_musicDetailFragment,
                            bundle
                        )
                        viewModel.clearBundle()
                    } else if (viewModel.checkMusic()) {
                        viewModel.clearBundle()
                    }
                }
                R.id.fragment_locker -> {}
                R.id.activity_record -> {
//                    if (viewModel.checkHasMument()) {
//                        bundle.putString(MUMENT_ID_FOR_EDIT, viewModel.mumentId.value)
//                        bundle.putSerializable(
//                            MUMENT_DETAIL_ENTITY,
//                            viewModel.mumentDetailContents.value
//                        )
//                    } else if (viewModel.checkMusic()) {
//                        bundle.putParcelable("music", viewModel.music.value)
//                    }
                }
                else -> {}

            }

            navController.navigate(item.itemId, bundle)
            viewModel.clearBundle()
            false
        }
    }

    private fun isRestrictUser() {
        viewModel.limitUser.observe(this) {
            if(it.restricted == true) {
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

    companion object {
        const val MUMENT_ID = "MUMENT_ID"
        const val MUSIC_ID = "MUSIC_ID"
    }

}