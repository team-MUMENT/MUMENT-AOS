package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentMyLikeBinding

class MyLikeFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMyLikeBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMyLikeBinding.inflate(inflater, container, false). run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}