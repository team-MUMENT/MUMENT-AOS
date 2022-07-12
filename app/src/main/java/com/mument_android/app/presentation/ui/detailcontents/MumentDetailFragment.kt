package com.mument_android.app.presentation.ui.detailcontents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mument_android.R
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentMumentDetailBinding
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class MumentDetailFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMumentDetailBinding>()
    private val viewModel: MumentDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMumentDetailBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mumentDetailViewModel= viewModel


    }
}