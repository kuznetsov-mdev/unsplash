package com.skillbox.unsplash.feature.collections.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentCollectionsBinding
import com.skillbox.unsplash.feature.collections.list.adapter.CollectionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollectionListFragment : Fragment(R.layout.fragment_collections) {
    private val viewBinding: FragmentCollectionsBinding by viewBinding()
    private val viewModel: CollectionListViewModel by viewModels()
    private val collectionAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CollectionAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCollectionList()
        viewModel.getCollections()
        observeCollections()
    }

    private fun observeCollections() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.collectionList.collectLatest(collectionAdapter::submitData)
        }
    }

    private fun initCollectionList() {
        with(viewBinding.collectionListView) {
            adapter = collectionAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}