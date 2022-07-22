package com.mument_android.app.presentation.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mument_android.R
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.presentation.base.BaseActivity
import com.mument_android.app.presentation.ui.detail.mument.EditMumentNavigator
import com.mument_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.mument_android.app.presentation.ui.record.RecordFragment.Companion.MUMENT_DETAIL_ENTITY
import com.mument_android.app.presentation.ui.record.RecordFragment.Companion.MUMENT_ID_FOR_EDIT
import com.mument_android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    EditMumentNavigator {
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.navBar, findNavController(R.id.nav_host))
        binding.navBar.setupWithNavController(navController)
        binding.navBar.setOnItemSelectedListener { item ->
            val bundle = Bundle()
            when (item.itemId) {
                R.id.fragment_home_frame -> {
                    if (viewModel.checkHasMusic()) {
                        bundle.putString("musicId", viewModel.musicId.value)
                    }
                }
                R.id.fragment_locker_frame -> {}
                R.id.fragment_record -> {
                    if (viewModel.checkHasBundle()) {
                        Timber.d("Has Bundle")
                        bundle.putString(MUMENT_ID_FOR_EDIT, viewModel.mumentId.value)
                        bundle.putParcelable(
                            MUMENT_DETAIL_ENTITY,
                            viewModel.mumentDetailContents.value
                        )
                    }else if(viewModel.checkMusic()){
                        Timber.d("Music!! ${viewModel.music.value}")
                        bundle.putParcelable("music", viewModel.music.value)
                    }
                }
                else -> {}
            }
            navController.navigate(item.itemId, bundle)
            viewModel.clearBundle()
            false
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

}