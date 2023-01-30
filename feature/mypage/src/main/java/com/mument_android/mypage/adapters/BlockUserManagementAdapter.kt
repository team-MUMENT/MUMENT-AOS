package com.mument_android.mypage.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.domain.entity.mypage.BlockUserEntity
import com.mument_android.mypage.databinding.ItemBlockUserBinding

class BlockUserManagementAdapter(
    val onClickDeleteUserItem: (userData: BlockUserEntity) -> Unit
) :
    ListAdapter<BlockUserEntity, BlockUserManagementAdapter.BlockUserViewHolder>(GlobalDiffCallBack<BlockUserEntity>()) {

    class BlockUserViewHolder(val binding: ItemBlockUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(blockUserData: BlockUserEntity) {
            binding.tvUserName.text = blockUserData.profileId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockUserViewHolder {
        val binding =
            ItemBlockUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BlockUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlockUserViewHolder, position: Int) {
        val userListPosition = getItem(position)
        holder.binding.btnUnblock.setOnClickListener {
            onClickDeleteUserItem.invoke(userListPosition)
        }
        holder.bind(userListPosition)
    }


}