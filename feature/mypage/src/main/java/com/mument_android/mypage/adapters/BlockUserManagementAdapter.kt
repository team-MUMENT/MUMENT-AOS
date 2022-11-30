package com.mument_android.mypage.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.mument_android.mypage.data.UserData
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.mypage.databinding.ItemBlockUserBinding

class BlockUserManagementAdapter:
        ListAdapter<UserData, BlockUserManagementAdapter.BlockUserViewHolder>(GlobalDiffCallBack<UserData>()) {

    class BlockUserViewHolder(private val binding: ItemBlockUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserData) {
            binding.tvUserName.text = userData.userID
            binding.ivBlockUser.load(userData.userImg){
                crossfade(true)
                placeholder(userData.userImg)
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
        holder.bind(getItem(position))
    }


}