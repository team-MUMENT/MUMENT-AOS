package com.mument_android.mypage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mument_android.mypage.adapters.BlockUserManagementAdapter
import com.mument_android.mypage.data.UserData
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.mypage.databinding.FragmentBlockUserManagementBinding

class BlockUserManagementFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentBlockUserManagementBinding>()
    private lateinit var blockUserManagementAdapter: BlockUserManagementAdapter
    private var blockUserList = mutableListOf<UserData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View= FragmentBlockUserManagementBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBlockUserRecyclerView()
    }

    private fun setBlockUserRecyclerView(){
        blockUserManagementAdapter = BlockUserManagementAdapter()
        binding.rvBlockUser.adapter = blockUserManagementAdapter

        blockUserManagementAdapter.submitList(blockUserList)
    }

}