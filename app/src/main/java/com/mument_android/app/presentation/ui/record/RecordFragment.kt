package com.mument_android.app.presentation.ui.record

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.flexbox.*
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentRecordBinding


class RecordFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentRecordBinding>()
    private var recordTagAdapter = RecordTagAdapter()

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
        clickEvent()
        countText()
    }

    private fun setEmotionalList() {
        with(binding.rvRecordEmotionalTags) {
            adapter = RecordTagAdapter()
            FlexboxLayoutManager(context).apply{
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW


            }.let {
                binding.rvRecordEmotionalTags.layoutManager =it
                binding.rvRecordEmotionalTags.adapter = recordTagAdapter
            }

            (adapter as RecordTagAdapter).submitList(EmotionalTag.values().map { it.tag })

//            EmotionalTag.values().map { requireContext().getString(it.tag) }.forEach {
//                Timber.e("tag: $it")
//            }
        }

        with(binding.rvRecordEmotionalTags2) {
            adapter = RecordTagAdapter()

            FlexboxLayoutManager(context).apply{
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW

            }.let {
                binding.rvRecordEmotionalTags2.layoutManager =it
                binding.rvRecordEmotionalTags2.adapter = recordTagAdapter
            }

            FlexboxLayoutManager(context).apply {
                alignItems = AlignItems.FLEX_START

            }.also { binding.rvRecordEmotionalTags2.layoutManager = it }
            val itemDecoration = FlexboxItemDecoration(context).apply {
                setDrawable(ContextCompat.getDrawable(context, R.drawable.rectangle_fill_blue3_20dp))
                setOrientation(FlexboxItemDecoration.HORIZONTAL)
            }
            if (binding.rvRecordEmotionalTags2.itemDecorationCount == 0) {
                binding.rvRecordEmotionalTags2.addItemDecoration(itemDecoration)
            }
            (adapter as RecordTagAdapter).submitList(EmotionalTag.values().map { it.tag })

//            EmotionalTag.values().map { requireContext().getString(it.tag) }.forEach {
//                Timber.e("tag: $it")
//            }
        }

    }

    private fun clickEvent(){
        binding.btnRecordFirst.changeButtonFont(true)
        with(binding){
            btnRecordFirst.setOnClickListener {
                btnRecordFirst.changeButtonFont(true)
                btnRecordSecond.changeButtonFont(false)
            }
        }

         with(binding){
             btnRecordSecond.setOnClickListener {
                 btnRecordFirst.changeButtonFont(false)
                 btnRecordSecond.changeButtonFont(true)

             }
         }

        binding.switchRecordSecret.setOnClickListener {
            if(binding.switchRecordSecret.isChecked){
                binding.tvRecordSecret.setText(R.string.record_secret)
            }else{
                binding.tvRecordSecret.setText(R.string.record_open)
            }
        }

    }

    private fun Button.changeButtonFont(selected: Boolean){
        isSelected = selected
        typeface = ResourcesCompat.getFont(context,if(isSelected) R.font.notosans_bold else R.font.notosans_medium)

    }



    private fun countText() {
        binding.etRecordWrite.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tvRecordTextNum.text = "0/1000"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var userinput = binding.etRecordWrite.text.toString()
                binding.tvRecordTextNum.text = userinput.length.toString() + "/1000"
            }

            override fun afterTextChanged(s: Editable?) {
                var userinput = binding.etRecordWrite.text.toString()
                binding.tvRecordTextNum.text = userinput.length.toString() + "/1000"
            }
        })
    }




}