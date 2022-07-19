package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetAdapter
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetSelectedAdapter
import com.mument_android.app.presentation.ui.locker.adapter.LockerTabAdapter
import com.mument_android.app.presentation.ui.locker.viewmodel.LockerViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentLockerBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LockerFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentLockerBinding>()
    private lateinit var lockerTabAdapter: LockerTabAdapter
    private lateinit var selectedAdapter: FilterBottomSheetSelectedAdapter
    private val viewModel: LockerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLockerBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivLockerList.isSelected = true
        binding.lifecycleOwner = viewLifecycleOwner
        initAdapter()
        initTab()
        listBtnClickListener()
        gridBtnClickListener()
        filterBtnClickListener()
        settingRecyclerView()
        //setSelectedTag()

    }

    override fun onResume() {
        super.onResume()
        //setSelectedTag()
        removeTag()

    }

    private fun initAdapter() {
        val fragmentList = listOf(MyMumentFragment(), MyLikeFragment())
        lockerTabAdapter = LockerTabAdapter(this)
        lockerTabAdapter.fragment.addAll(fragmentList)
        binding.vpLocker.adapter = lockerTabAdapter


    }

    private fun initTab() {
        val tabLabel = listOf("나의 뮤멘트", "좋아요한 뮤멘트")
        TabLayoutMediator(binding.tlLocker, binding.vpLocker) { tab, position ->
            tab.text = tabLabel[position]
        }.attach()

        binding.root.setOnClickListener {
            //findNavController().navigate(R.id.action_lockerFragment_to_mumentDetailFragment)
        }

    }


    private fun listBtnClickListener() {
        binding.ivLockerList.setOnClickListener {
            viewModel.changeIsGridLayout(false)
            binding.ivLockerList.isSelected = true
            binding.ivLockerGrid.isSelected = false

        }
    }

    private fun gridBtnClickListener() {
        binding.ivLockerGrid.setOnClickListener {
            viewModel.changeIsGridLayout(true)
            binding.ivLockerList.isSelected = false
            binding.ivLockerGrid.isSelected = true
        }
    }

    private fun filterBtnClickListener() {
        binding.ivLockerFilter.setOnClickListener {
            LockerFilterBottomSheetFragment.newInstance()
                .show(parentFragmentManager, "LockerFilterBottomSheetFragment")
        }
    }


    private fun settingRecyclerView() {
        removeTag()
        //setSelectedTag()
        viewModel.realTagList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.rvSelectedTags.visibility = View.GONE
            } else {
                binding.rvSelectedTags.visibility = View.VISIBLE
                //selectedAdapter.submitList(it)
            }
        }
    }

    //선택된 태그들 리사이클러뷰
    private fun setSelectedTag() {

        binding.rvSelectedTags.run {
            selectedAdapter = FilterBottomSheetSelectedAdapter { tag, idx ->
                viewModel.removeCheckedList(tag)
                Timber.d("Test : $tag")
                viewModel.realTagList.observe(viewLifecycleOwner) {
                    Timber.d("jebal : $it")
                    (selectedAdapter as FilterBottomSheetSelectedAdapter).submitList(it)
                }
            }
            binding.rvSelectedTags.adapter = selectedAdapter

            selectedAdapter.selectedTags = viewModel.realTagList?.value!!.toMutableList()
            selectedAdapter.submitList(viewModel.realTagList.value)
        }

        /*
        binding.rvSelectedTags.adapter = selectedAdapter

        selectedAdapter.selectedTags = viewModel.realTagList?.value!!.toMutableList()
        selectedAdapter.submitList(viewModel.realTagList.value)
         */
    }

    private fun removeTag() {
        binding.rvSelectedTags.run {
            adapter = FilterBottomSheetSelectedAdapter { tag, idx ->
                viewModel.removeCheckedList(tag)
            }

            viewModel.checkedTagList.observe(viewLifecycleOwner) {
                (adapter as FilterBottomSheetSelectedAdapter).submitList(it)
                if (it.isEmpty()) {
                    binding.rvSelectedTags.visibility = View.GONE
                } else {
                    binding.rvSelectedTags.visibility = View.VISIBLE
                    //selectedAdapter.submitList(it)
                }
            }
        }

    }
}