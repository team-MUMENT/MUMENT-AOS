package com.mument_android.detail.mument

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.mument_android.detail.databinding.FragmentMumentToShareDialogBinding
import com.mument_android.domain.entity.detail.MumentEntity

class ShareMumentView(
    parent: ViewGroup,
    mument: MumentEntity,
    context: Context,
    attrs: AttributeSet? = null
): ViewGroup(context, attrs) {
    private lateinit var binding: FragmentMumentToShareDialogBinding
    init {
        val inflater = LayoutInflater.from(context)
        binding = FragmentMumentToShareDialogBinding.inflate(inflater, parent, true)
        renderMument(mument, parent)
    }

    override fun onLayout(p0: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        val horizontalPadding = (width * 0.09).toInt()
//        val verticalPadding = (height * 0.18).toInt()
//        binding.root.rootView.layout(horizontalPadding, verticalPadding, width - horizontalPadding, height - verticalPadding)
    }

    private fun renderMument(mument: MumentEntity, parent: ViewGroup) {
//        binding.root.layoutParams = (parent.layoutParams).apply {
//            width = (parent.width * 0.8).toInt()
//            height = (parent.height * 0.6).toInt()
//        }

        with(binding) {
            ivProfileImage.load(mument.writerInfo.profileImage)
            ivAlbumCover.load(mument.musicInfo.thumbnail)
            tvWriterName.text = mument.writerInfo.name
            tvMusicName.text = mument.musicInfo.name
            tvMument.text = mument.content
            tvDate.text = mument.createdDate
        }
    }
}