package com.mument_android.app.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.mument_android.R
import com.mument_android.app.presentation.RestrictUserDialog
import com.mument_android.app.presentation.ui.detail.mument.navigator.EditMumentNavigator
import com.mument_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
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

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
        intent.getStringExtra("MUMENT_ID")?.let { mumentId ->
            intent.getStringExtra("MUSIC_INFO")?.let { music ->
                val bundle = Bundle().also {
                    it.putString(MUMENT_ID, mumentId)
                    it.putString("MUSIC_INFO", music)
                }
                navController.navigate(R.id.action_homeFragment_to_nav_detail, bundle)
            }
        }
        floatingBtnListener()
        customAppBar()
        isLimitUserNetwork()
        isRestrictUser()
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
//        binding.navBar.setupWithNavController(navController)

        binding.navBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> changeCurrentFragment(R.id.homeFragment)
                R.id.lockerFragment -> changeCurrentFragment(R.id.lockerFragment)
            }
            false
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e("onNewIntent", intent.toString())
    }

    companion object {
        const val MUMENT_ID = "MUMENT_ID"
        const val MUSIC_ID = "MUSIC_ID"
    }

}