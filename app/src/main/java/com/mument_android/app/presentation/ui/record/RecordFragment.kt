package com.mument_android.app.presentation.ui.record

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.presentation.ui.record.viewmodel.RecodeViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentHomeBinding
import com.mument_android.databinding.FragmentRecordBinding
import timber.log.Timber

class RecordFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentRecordBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRecordBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEmotionalList()
    }

    private fun setEmotionalList() {
        with(binding.rvEmotionalTags) {
            adapter = RecordTagAdapter()
            (adapter as RecordTagAdapter).submitList(EmotionalTag.values().map { it.tag })
            EmotionalTag.values().map { requireContext().getString(it.tag) }.forEach {
                Timber.e("tag: $it")
            }
        }
    }
}