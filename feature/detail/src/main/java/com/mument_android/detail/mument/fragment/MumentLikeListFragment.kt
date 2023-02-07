package com.mument_android.detail.mument.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.detail.databinding.FragmentMumentLikeListBinding
import com.mument_android.detail.mument.adapter.UserLikeMumentListAdapter
import com.mument_android.domain.entity.user.UserEntity


class MumentLikeListFragment: Fragment() {
    private var binding by AutoClearedValue<FragmentMumentLikeListBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMumentLikeListBinding.inflate(inflater).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserListRecyclerView()
        fetchUserList()
        popBackStack()
    }

    private fun setUserListRecyclerView() {
        binding.rvUsers.run {
            adapter = UserLikeMumentListAdapter()
        }
    }

    private fun fetchUserList() {
        arguments?.getParcelableArrayList<UserEntity>(USER_LIST_KEY)?.let {
            renderUserList(it)
        }
    }

    private fun renderUserList(userList: List<UserEntity>) {
        (binding.rvUsers.adapter as UserLikeMumentListAdapter).submitList(userList)
    }

    private fun popBackStack() {
        binding.ivBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        const val USER_LIST_KEY = "USER_LIST_KEY"
    }

}