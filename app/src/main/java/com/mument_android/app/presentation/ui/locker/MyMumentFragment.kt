package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mument_android.R
import com.mument_android.app.presentation.ui.locker.adapter.LockerMumentAdapter
import com.mument_android.app.presentation.ui.locker.adapter.LockerTimeAdapter
import com.mument_android.app.presentation.ui.locker.viewmodel.LockerViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentLockerBinding
import com.mument_android.databinding.FragmentMyMumentBinding

class MyMumentFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMyMumentBinding>()
    private val viewModel: LockerViewModel by viewModels()
    private lateinit var lockerMumentAdapter: LockerMumentAdapter
    private lateinit var lockerTimeAdapter: LockerTimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMyMumentBinding.inflate(inflater, container, false). run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setAdapter()
        setListData()
    }

    private fun setAdapter() {
        lockerTimeAdapter = LockerTimeAdapter()
        lockerMumentAdapter = LockerMumentAdapter()
    }

    private fun setListData() {

    }
}