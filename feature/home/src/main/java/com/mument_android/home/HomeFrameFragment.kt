package com.mument_android.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.home.databinding.FragmentHomeFrameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFrameFragment: Fragment() {
    private var binding by AutoClearedValue<FragmentHomeFrameBinding>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeFrameBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}