package com.mument_android.mypage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mument_android.core_dependent.ui.MumentDialog
import com.mument_android.core_dependent.ui.MumentDialogBuilder
import com.mument_android.mypage.adapters.BlockUserManagementAdapter
import com.mument_android.mypage.data.UserData
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.mypage.MyPageViewModel
import com.mument_android.mypage.R
import com.mument_android.mypage.databinding.FragmentBlockUserManagementBinding

class BlockUserManagementFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentBlockUserManagementBinding>()
    private val myPageViewModel: MyPageViewModel by viewModels()
    private lateinit var blockUserManagementAdapter: BlockUserManagementAdapter
    private var blockUserList = mutableListOf<UserData>()


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
    }

    private fun setBlockUserRecyclerView() {
        blockUserManagementAdapter =
            BlockUserManagementAdapter(onClickDeleteUserItem = { deleteItem() })
        binding.rvBlockUser.adapter = blockUserManagementAdapter
        blockUserList.addAll(
            listOf(
                UserData(R.drawable.mument_profile_love_60, "진실"),
                UserData(R.drawable.mument_profile_love_60, "heaven00"),
                UserData(R.drawable.mument_profile_love_60, "SonPyeongHwa"),
                UserData(R.drawable.mument_profile_love_60, "mino")
            )
        )
        blockUserManagementAdapter.submitList(blockUserList)
    }

    private fun deleteItem() {
        MumentDialogBuilder()
            .setHeader(getString(R.string.unblock_title))
            .setBody(getString(R.string.unblock_body))
            .setOption(MumentDialog.DIALOG_UNBLOCK)
            .setAllowListener {
                myPageViewModel.userList.observe(viewLifecycleOwner) { list ->
                    blockUserManagementAdapter.submitList(list)
                }
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