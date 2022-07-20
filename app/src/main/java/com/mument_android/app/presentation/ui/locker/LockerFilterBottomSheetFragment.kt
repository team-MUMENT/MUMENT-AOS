package com.mument_android.app.presentation.ui.locker

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.R
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetAdapter
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetSelectedAdapter
import com.mument_android.app.presentation.ui.locker.viewmodel.LockerViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.app.util.ViewUtils.snackBar
import com.mument_android.databinding.FragmentLockerFilterBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockerFilterBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding by AutoClearedValue<FragmentLockerFilterBottomSheetBinding>()
    private val lockerViewModel: LockerViewModel by activityViewModels()
    private lateinit var filterBottomSheetAdapterImpress: FilterBottomSheetAdapter
    private lateinit var filterBottomSheetAdpaterEmotion: FilterBottomSheetAdapter


    companion object {
        @JvmStatic
        private var INSTANCE: LockerFilterBottomSheetFragment? = null

        @JvmStatic
        fun newInstance(): LockerFilterBottomSheetFragment {
            return INSTANCE ?: LockerFilterBottomSheetFragment().apply {
                INSTANCE = this
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLockerFilterBottomSheetBinding.inflate(inflater, container, false).run {
        binding = this
        this.root

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener { dialogInterface ->
            ((dialogInterface as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View).apply {
                val behavior = BottomSheetBehavior.from(this)
                val layoutParams = this.layoutParams
                behavior.disableShapeAnimations()
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                this.layoutParams = layoutParams
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEmotionalList()
        setSelectedTag()
        closeBtnListener()
        resetTags()
        selectLayout()
        applyBtnListener()
    }
    private fun setEmotionalList() {
        filterBottomSheetAdapterImpress = FilterBottomSheetAdapter(
            requireContext(),
            object : FilterBottomSheetAdapter.FilterTagCheckListener {
                override fun addCheckedTag(tag: TagEntity) {
                    lockerViewModel.addCheckedList(tag)
                    filterBottomSheetAdpaterEmotion.selectedTags.add(tag)
                }

                override fun removeCheckedTag(tag: TagEntity) {
                    filterBottomSheetAdpaterEmotion.selectedTags.remove(tag)
                    lockerViewModel.removeCheckedList(tag)
                }

                override fun alertMaxCount() {
                    requireContext().snackBar(binding.root.rootView, "태그는 최대 3개까지 선택 할 수 있어요.")
                }

            }
        )

        filterBottomSheetAdpaterEmotion = FilterBottomSheetAdapter(requireContext(),
            object : FilterBottomSheetAdapter.FilterTagCheckListener {
                override fun addCheckedTag(tag: TagEntity) {
                    filterBottomSheetAdapterImpress.selectedTags.add(tag)
                    lockerViewModel.addCheckedList(tag)
                }

                override fun removeCheckedTag(tag: TagEntity) {
                    filterBottomSheetAdapterImpress.selectedTags.remove(tag)
                    lockerViewModel.removeCheckedList(tag)
                }

                override fun alertMaxCount() {
                    requireContext().snackBar(
                        binding.root.rootView,
                        "태그는 최대 3개까지 선택 할 수 있어요."
                    )
                }

            }
        )

        with(binding.rvEmotion) {
            setItemDecoration(this)
            setEmotionalRvFlexBoxLayout()
        }

        with(binding.rvImpressive) {
            setItemDecoration(this)
            setImpressiveRvFlexBoxLayout()
        }
    }

    private fun closeBtnListener() {
        binding.ivFilterDelete.setOnClickListener {
            lockerViewModel.checkedTagList.value = lockerViewModel.realTagList.value

            dismiss()
        }
    }

    //선택된 태그들 리사이클러뷰
    private fun setSelectedTag() {
        binding.rvSelectedTags.run {
            adapter = FilterBottomSheetSelectedAdapter { tag, idx ->
                if(lockerViewModel.emotionalTags.contains(tag)) {
                    filterBottomSheetAdpaterEmotion.selectedTags.remove(tag)
                    binding.rvEmotion.syncSelectedTags(filterBottomSheetAdpaterEmotion.currentList.indexOf(tag))
                } else {
                    filterBottomSheetAdapterImpress.selectedTags.remove(tag)
                    binding.rvImpressive.syncSelectedTags(filterBottomSheetAdapterImpress.currentList.indexOf(tag))
                }

                lockerViewModel.removeCheckedList(tag)
            }

            lockerViewModel.checkedTagList.observe(viewLifecycleOwner) {
                (adapter as FilterBottomSheetSelectedAdapter).submitList(it)
            }


        }
    }

    private fun RecyclerView.syncSelectedTags(position: Int) {
        val view = if (id == binding.rvEmotion.id) binding.rvEmotion[position] else binding.rvImpressive[position]
        val viewHolder = getChildViewHolder(view)
        (viewHolder as FilterBottomSheetAdapter.BottomSheetFilterHolder).binding.cbTag.isChecked = false
    }


    //recyclerview item decoration
    private fun setItemDecoration(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(
            RecyclerviewItemDivider(
                7.dpToPx(requireContext()),
                5.dpToPx(requireContext()),
                RecyclerviewItemDivider.IS_GRIDLAYOUT
            )
        )
    }

    private fun setImpressiveRvFlexBoxLayout() {
        with(binding.rvImpressive) {
            FlexboxLayoutManager(context).apply {
                flexWrap = com.google.android.flexbox.FlexWrap.WRAP
                flexDirection = com.google.android.flexbox.FlexDirection.ROW
            }.let {
                layoutManager = it
                adapter = filterBottomSheetAdapterImpress
            }
            filterBottomSheetAdapterImpress.submitList(lockerViewModel.impressionTags)
            filterBottomSheetAdapterImpress.selectedTags = lockerViewModel.checkedTagList.value!!.toMutableList()

        }
    }

    private fun setEmotionalRvFlexBoxLayout() {
        with(binding.rvEmotion) {
            FlexboxLayoutManager(context).apply {
                flexWrap = com.google.android.flexbox.FlexWrap.WRAP
                flexDirection = com.google.android.flexbox.FlexDirection.ROW
            }.let {
                layoutManager = it
                adapter = filterBottomSheetAdpaterEmotion
            }
            filterBottomSheetAdpaterEmotion.submitList(lockerViewModel.emotionalTags)
            filterBottomSheetAdpaterEmotion.selectedTags = lockerViewModel.checkedTagList.value!!.toMutableList()
        }
    }

    //초기화 버튼
    private fun resetTags() {
        binding.tvClearAll.setOnClickListener {
            binding.rvEmotion.resetCheckTags(filterBottomSheetAdpaterEmotion)
            binding.rvImpressive.resetCheckTags(filterBottomSheetAdapterImpress)
            filterBottomSheetAdapterImpress.selectedTags.clear()
            filterBottomSheetAdpaterEmotion.selectedTags.clear()
            lockerViewModel.resetCheckedList()
        }
    }


    private fun RecyclerView.resetCheckTags(adapter: FilterBottomSheetAdapter) {
        (0 until adapter.itemCount).forEach {
            getChildViewHolder(get(it)).run {
                (this as FilterBottomSheetAdapter.BottomSheetFilterHolder).binding.cbTag.isChecked =
                    false
            }
        }
    }

    //bottomsheet background 설정
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    private fun selectLayout() {
        lockerViewModel.checkedTagList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.clSelectedTag.visibility = View.GONE
                binding.tvFilterNum.setTextColor(Color.parseColor("#B6B6B6"))
            } else {
                binding.clSelectedTag.visibility = View.VISIBLE
                binding.tvFilterNum.setTextColor(Color.parseColor("#2AC9fB"))
            }

            val value = it.count().toString()
            binding.tvFilterNum.setText(value)
        }
    }

    //완료버튼 클릭 리스너
    private fun applyBtnListener() {
        binding.tvApprove.setOnClickListener {
            lockerViewModel.realTagList.value = lockerViewModel.checkedTagList.value
            dismiss()
        }
    }

}