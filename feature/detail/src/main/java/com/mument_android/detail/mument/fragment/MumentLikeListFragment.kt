package com.mument_android.detail.mument.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.detail.databinding.FragmentMumentLikeListBinding
import com.mument_android.detail.mument.adapter.UserLikeMumentListAdapter
import com.mument_android.detail.mument.viewmodel.MumentLikeViewModel
import com.mument_android.domain.entity.user.UserEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MumentLikeListFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMumentLikeListBinding>()
    private val mumentLikeListViewModel: MumentLikeViewModel by viewModels()
    private val usersLikePageAdapter = UserLikeMumentListAdapter()

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
        binding.lifecycleOwner = viewLifecycleOwner
        setUserListRecyclerView()
        fetchUserList()
        renderUserList()
        popBackStack()
    }

    private fun setUserListRecyclerView() {
        binding.rvUsers.run {
            adapter = usersLikePageAdapter
        }
    }

    private fun fetchUserList() {
        arguments?.getString(MUMENT_ID)?.let {
            mumentLikeListViewModel.fetchUsersLikeList(it)
        }
    }

    private fun renderUserList() {
        collectFlowWhenStarted(mumentLikeListViewModel.usersLikeList) { userList ->
            usersLikePageAdapter.submitData(userList)
        }
    }

    private fun popBackStack() {
        binding.ivBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}