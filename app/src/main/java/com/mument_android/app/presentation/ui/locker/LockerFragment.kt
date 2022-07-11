package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mument_android.R
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentLockerBinding

class LockerFragment : Fragment() {
   private var binding by AutoClearedValue<FragmentLockerBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLockerBinding.inflate(inflater, container, false). run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_lockerFragment_to_mumentDetailFragment)
        }
    }
}