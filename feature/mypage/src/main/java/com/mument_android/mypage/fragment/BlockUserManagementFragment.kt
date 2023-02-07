package com.mument_android.mypage.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mument_android.core.network.ApiResult
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ui.MumentDialogBuilder
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.domain.entity.mypage.BlockUserEntity
import com.mument_android.mypage.MyPageViewModel
import com.mument_android.mypage.R
import com.mument_android.mypage.adapters.BlockUserManagementAdapter
import com.mument_android.mypage.databinding.FragmentBlockUserManagementBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockUserManagementFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentBlockUserManagementBinding>()
    private val myPageViewModel: MyPageViewModel by viewModels()
    private lateinit var blockUserManagementAdapter: BlockUserManagementAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentBlockUserManagementBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myPageViewModel = myPageViewModel

        setBlockUserRecyclerView()
        backBtnListener()
        myPageViewModel.checkBlockUserEmpty()
    }

    private fun setBlockUserRecyclerView() {
        blockUserManagementAdapter =
            BlockUserManagementAdapter(onClickDeleteUserItem = { deleteItem(it) })
        binding.rvBlockUser.adapter = blockUserManagementAdapter
        myPageViewModel.fetchBlockUserList()

        collectFlowWhenStarted(myPageViewModel.blockUserList) {
            when (it) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    blockUserManagementAdapter.submitList(it.data)
                }
                else -> {}
            }

        }


    }

    private fun deleteItem(data:BlockUserEntity) {
        MumentDialogBuilder()
            .setHeader(getString(R.string.unblock_title))
            .setBody(getString(R.string.unblock_body))
            .setAllowListener("차단해제") {
                myPageViewModel.deleteBlockUser(data.id)
            }
            .setCancelListener {}
            .build()
            .show(childFragmentManager, this.tag)
    }

    private fun backBtnListener() {
        binding.btnBlockUserManagementBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}