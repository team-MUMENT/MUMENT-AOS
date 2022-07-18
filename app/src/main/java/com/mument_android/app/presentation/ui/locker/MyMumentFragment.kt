package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mument_android.app.presentation.ui.locker.adapter.LockerTimeAdapter
import com.mument_android.app.presentation.ui.locker.viewmodel.LockerViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentMyMumentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyMumentFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMyMumentBinding>()
    private val viewModel: LockerViewModel by viewModels( ownerProducer = { requireParentFragment() } )
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
        setMyMumentListAdapter()
        emptyBtnClick()

    }

    private fun setMyMumentListAdapter() {
        binding.rvMumentLinear.run {
            viewModel.myMuments.observe(viewLifecycleOwner) {
                initMumentEmpty(it.size)
                (adapter as LockerTimeAdapter).submitList(it)
            }
            viewModel.isGridLayout.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { isGridLayout ->
                adapter = LockerTimeAdapter(isGridLayout)
                (adapter as LockerTimeAdapter).submitList(viewModel.myMuments.value)
            }
        }
    }

    private fun initMumentEmpty(size : Int) {
        if(size == 0) {
            binding.clEmptyView.visibility = View.VISIBLE
        } else {
            binding.clEmptyView.visibility = View.GONE
        }
    }

    private fun emptyBtnClick() {
        binding.clEmptyRecord.setOnClickListener {
            //기록하기 뷰로 이동
        }
    }
}