package com.mument_android.app.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mument_android.app.data.network.home.adapter.SearchListAdapter
import com.mument_android.app.presentation.ui.home.viewmodel.SearchViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentSearchBinding
import timber.log.Timber

class SearchFragment : Fragment() {

    private var binding by AutoClearedValue<FragmentSearchBinding>()
    private val viewmodel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAdapter = SearchListAdapter({}, {})
        binding.rcSearch.adapter = searchAdapter
        searchAdapter.submitList(viewmodel.searchList)
        binding.option = true
        binding.etSearch.setOnClickListener {
        }
        binding.etSearch.setOnFocusChangeListener { view, b ->
            Timber.d("Focus!!! $b")
            if(b){
                binding.ivDelete.visibility = View.VISIBLE
            }else{
                binding.ivDelete.visibility = View.GONE
            }
        }
    }

}