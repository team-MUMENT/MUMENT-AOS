package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mument_android.app.presentation.ui.locker.adapter.LockerTimeAdapter
import com.mument_android.app.presentation.ui.locker.viewmodel.LockerViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentMyLikeBinding

class MyLikeFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMyLikeBinding>()
    private val viewModel: LockerViewModel by viewModels( ownerProducer = { requireParentFragment() } )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMyLikeBinding.inflate(inflater, container, false). run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setMyMumentListAdapter()
    }

    private fun setMyMumentListAdapter() {
        binding.rvLikeLinear.run {
            viewModel.myMuments.observe(viewLifecycleOwner) {
                initReviewEmpty(it.size)
                (adapter as LockerTimeAdapter).submitList(it)
            }
            viewModel.isGridLayout.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { isGridLayout ->
                adapter = LockerTimeAdapter(isGridLayout)
                (adapter as LockerTimeAdapter).submitList(viewModel.myMuments.value)
            }
        }
    }

    //1:1질문 엠티뷰
    private fun initReviewEmpty(size : Int){
        if(size == 0){
            binding.clEmptyView.visibility = View.VISIBLE
        }else{
            binding.clEmptyView.visibility = View.GONE
        }
    }
}