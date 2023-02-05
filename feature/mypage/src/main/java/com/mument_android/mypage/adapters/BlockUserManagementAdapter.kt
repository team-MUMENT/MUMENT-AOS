package com.mument_android.mypage.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.domain.entity.mypage.BlockUserEntity
import com.mument_android.mypage.R
import com.mument_android.mypage.databinding.ItemBlockUserBinding

class BlockUserManagementAdapter(
    val onClickDeleteUserItem: (userData: BlockUserEntity) -> Unit
) :
    ListAdapter<BlockUserEntity, BlockUserManagementAdapter.BlockUserViewHolder>(GlobalDiffCallBack<BlockUserEntity>()) {

    class BlockUserViewHolder(val binding: ItemBlockUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(blockUserData: BlockUserEntity) {
            binding.tvUserName.text = blockUserData.profileId
            binding.ivBlockUser.load(blockUserData.image) {
                crossfade(true)
                placeholder(R.drawable.mument_profile_sad_60_1)
                transformations(CircleCropTransformation())
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockUserViewHolder {
        val binding =
            ItemBlockUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BlockUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlockUserViewHolder, position: Int) {
        val userData = getItem(position)
        holder.binding.btnUnblock.setOnClickListener {
            onClickDeleteUserItem.invoke(userData)
        }
        holder.bind(userData)
    }


}