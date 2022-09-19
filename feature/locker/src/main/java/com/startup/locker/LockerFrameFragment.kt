package com.startup.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.startup.locker.viewmodels.LockerViewModel
import com.startup.core_dependent.util.AutoClearedValue
import com.startup.locker.databinding.FragmentLockerFrameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockerFrameFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentLockerFrameBinding>()
    private val lockerViewModel: LockerViewModel by viewModels( ownerProducer = { requireParentFragment() } )
    private val viewModel: LockerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLockerFrameBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}