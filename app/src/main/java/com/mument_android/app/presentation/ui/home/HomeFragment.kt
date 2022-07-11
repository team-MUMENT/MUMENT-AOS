package com.mument_android.app.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mument_android.R
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.entity.MumentCardData.Music
import com.mument_android.app.domain.entity.MumentCardData.User
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentHomeBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}