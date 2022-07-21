package com.mument_android.app.presentation.ui.locker.filter

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.R
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetAdapter
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetSelectedAdapter
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.app.util.ViewUtils.snackBar
import com.mument_android.databinding.FragmentLockerLikeFilterBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockerLikeFilterBottomSheetFragment(
    private val initialTags: List<TagEntity>
) : BottomSheetDialogFragment() {
    private var binding by AutoClearedValue<FragmentLockerLikeFilterBottomSheetBinding>()
    private lateinit var filterBottomSheetAdapterImpress: FilterBottomSheetAdapter
    private lateinit var filterBottomSheetAdpaterEmotion: FilterBottomSheetAdapter
    private lateinit var completeSelectListener: (List<TagEntity>) -> Unit
    private val lockerFilterViewModel: LockerFilterViewModel by viewModels()


    companion object {
        @JvmStatic
        private var INSTANCE: LockerLikeFilterBottomSheetFragment? = null

        @JvmStatic
        fun newInstance(
            initialTags: List<TagEntity>,
            completeSelectListener: (List<TagEntity>) -> Unit
        ): LockerLikeFilterBottomSheetFragment {
            return INSTANCE ?: LockerLikeFilterBottomSheetFragment(initialTags).apply {
                this.completeSelectListener = completeSelectListener
                INSTANCE = this
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLockerLikeFilterBottomSheetBinding.inflate(inflater, container, false).run {
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
        binding.lifecycleOwner = viewLifecycleOwner
        lockerFilterViewModel.addLikeInitialTags(initialTags)
        //lockerFilterViewModel.changeLikeInitialSelectedTags(initialTags)

        setEmotionalList()
        updateSelectedTags()
        setSelectedTag()
        closeBtnListener()
        resetTags()
        applyBtnListener()
    }


    private fun setEmotionalList() {
        filterBottomSheetAdapterImpress = FilterBottomSheetAdapter(
            requireContext(),
            object : FilterBottomSheetAdapter.FilterTagCheckListener {
                override fun addCheckedTag(tag: TagEntity) {
                    lockerFilterViewModel.addLikeSelectedTag(tag)
                    filterBottomSheetAdpaterEmotion.selectedTags.add(tag)
                }

                override fun removeCheckedTag(tag: TagEntity) {
                    lockerFilterViewModel.removeLikeSelectedTag(tag)
                    filterBottomSheetAdpaterEmotion.selectedTags.remove(tag)

                }

                override fun alertMaxCount() {
                    requireContext().snackBar(binding.root.rootView, "태그는 최대 3개까지 선택 할 수 있어요.")
                }

            }
        )

        filterBottomSheetAdpaterEmotion = FilterBottomSheetAdapter(requireContext(),
            object : FilterBottomSheetAdapter.FilterTagCheckListener {
                override fun addCheckedTag(tag: TagEntity) {
                    lockerFilterViewModel.addLikeSelectedTag(tag)
                    filterBottomSheetAdapterImpress.selectedTags.add(tag)
                }

                override fun removeCheckedTag(tag: TagEntity) {
                    lockerFilterViewModel.removeLikeSelectedTag(tag)
                    filterBottomSheetAdapterImpress.selectedTags.remove(tag)

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

    private fun updateSelectedTags() {
        lockerFilterViewModel.likeSelectedTags.observe(viewLifecycleOwner) {
            (binding.rvSelectedTags.adapter as FilterBottomSheetSelectedAdapter).submitList(it)
            selectLayout(it)
        }
    }


    //선택된 태그들 리사이클러뷰
    private fun setSelectedTag() {
        binding.rvSelectedTags.run {
            adapter = FilterBottomSheetSelectedAdapter { tag, idx ->
                if (lockerFilterViewModel.emotionalTags.contains(tag)) {
                    binding.rvEmotion.syncSelectedTags(filterBottomSheetAdpaterEmotion.currentList.indexOf(tag), false)
                } else { binding.rvImpressive.syncSelectedTags(filterBottomSheetAdapterImpress.currentList.indexOf(tag), false)
                }

                filterBottomSheetAdpaterEmotion.selectedTags.remove(tag)
                filterBottomSheetAdapterImpress.selectedTags.remove(tag)
                lockerFilterViewModel.removeLikeSelectedTag(tag)
            }
        }
    }


    private fun RecyclerView.syncSelectedTags(position: Int, check: Boolean) {
        val view =
            if (id == binding.rvEmotion.id) binding.rvEmotion[position] else binding.rvImpressive[position]
        val viewHolder = getChildViewHolder(view)
        (viewHolder as FilterBottomSheetAdapter.BottomSheetFilterHolder).binding.cbTag.isChecked = check
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
            filterBottomSheetAdapterImpress.selectedTags.addAll(initialTags)
            filterBottomSheetAdapterImpress.submitList(lockerFilterViewModel.impressionTags)

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
            filterBottomSheetAdpaterEmotion.selectedTags.addAll(initialTags)
            filterBottomSheetAdpaterEmotion.submitList(lockerFilterViewModel.emotionalTags)
        }
    }

    //초기화 버튼
    private fun resetTags() {
        binding.tvClearAll.setOnClickListener {
            binding.rvEmotion.resetCheckTags(filterBottomSheetAdpaterEmotion)
            binding.rvImpressive.resetCheckTags(filterBottomSheetAdapterImpress)
            filterBottomSheetAdapterImpress.selectedTags.clear()
            filterBottomSheetAdpaterEmotion.selectedTags.clear()
            lockerFilterViewModel.clearLikeSelectedTags()
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


    private fun selectLayout(tags: List<TagEntity>) {
        if (tags.isEmpty()) {
            binding.clSelectedTag.visibility = View.GONE
            binding.tvFilterNum.setTextColor(Color.parseColor("#B6B6B6"))
        } else {
            binding.clSelectedTag.visibility = View.VISIBLE
            binding.tvFilterNum.setTextColor(Color.parseColor("#2AC9fB"))
        }

        binding.tvFilterNum.text = tags.size.toString()

    }

    //완료버튼 클릭 리스너
    private fun applyBtnListener() {
        binding.tvApprove.setOnClickListener {
            selectLayout(lockerFilterViewModel.likeSelectedTags.value ?: listOf())
            completeSelectListener(lockerFilterViewModel.likeSelectedTags.value ?: listOf())
            dismiss()
        }
    }


    private fun closeBtnListener() {
        binding.ivFilterDelete.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        INSTANCE = null
    }

    //bottomsheet background 설정
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

}