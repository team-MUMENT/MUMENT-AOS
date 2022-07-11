package com.mument_android.app.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mument_android.R
import com.mument_android.app.presentation.ui.home.viewmodel.HomeViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentHomeBinding>()
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.homeViewModel = viewModel
        binding.emojiTvHome.setOnClickListener {
            viewModel.setRandomTags()
        }
        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mumentDetailFragment)
        }
    }
}