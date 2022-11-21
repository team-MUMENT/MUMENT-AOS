package com.mument_android.app.presentation.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mument_android.R
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.presentation.ui.detail.mument.navigator.EditMumentNavigator
import com.mument_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.mument_android.record.RecordFragment.Companion.MUMENT_DETAIL_ENTITY
import com.mument_android.record.RecordFragment.Companion.MUMENT_ID_FOR_EDIT
import com.mument_android.databinding.ActivityMainBinding
import com.mument_android.core_dependent.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    EditMumentNavigator {
    lateinit var navController: NavController
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {
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
                R.id.fragment_record -> {
                    if (viewModel.checkHasMument()) {
                        bundle.putString(MUMENT_ID_FOR_EDIT, viewModel.mumentId.value)
                        bundle.putSerializable(
                            MUMENT_DETAIL_ENTITY,
                            viewModel.mumentDetailContents.value
                        )
                    }else if(viewModel.checkMusic()){
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
    companion object {
        const val MUMENT_ID = "MUMENT_ID"
        const val MUSIC_ID = "MUSIC_ID"
    }

}