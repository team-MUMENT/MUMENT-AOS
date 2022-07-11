package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.mument_android.R
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentLockerFrameBinding

class LockerFrameFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentLockerFrameBinding>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLockerFrameBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_home_frame) as NavHostFragment
        navController = navHostFragment.navController

    }

}