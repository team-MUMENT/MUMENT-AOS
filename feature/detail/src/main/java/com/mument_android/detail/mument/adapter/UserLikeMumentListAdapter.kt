package com.mument_android.detail.mument.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.detail.BR
import com.mument_android.detail.databinding.ItemUserLikeMumentBinding
import com.mument_android.domain.entity.user.UserEntity

class UserLikeMumentListAdapter :
    PagingDataAdapter<UserEntity, UserLikeMumentListAdapter.UserLikeMumentListViewHolder>(
        GlobalDiffCallBack<UserEntity>()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserLikeMumentListViewHolder {
        val binding =
            ItemUserLikeMumentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserLikeMumentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserLikeMumentListViewHolder, position: Int) {
        val userData = getItem(holder.absoluteAdapterPosition)
        if (userData != null) {
            holder.binding.setVariable(BR.user, userData)
        }
    }

    class UserLikeMumentListViewHolder(val binding: ItemUserLikeMumentBinding) :
        RecyclerView.ViewHolder(binding.root)
}