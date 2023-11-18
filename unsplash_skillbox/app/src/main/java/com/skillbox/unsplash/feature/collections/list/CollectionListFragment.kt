package com.skillbox.unsplash.feature.collections.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentCollectionsBinding
import com.skillbox.unsplash.feature.collections.list.adapter.CollectionAdapter
import com.skillbox.unsplash.feature.collections.model.CollectionUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollectionListFragment : Fragment(R.layout.fragment_collections) {
    private val viewBinding: FragmentCollectionsBinding by viewBinding()
    private val viewModel: CollectionListViewModel by viewModels()
    private val collectionAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CollectionAdapter(
            ::navigateToDetailInfo
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCollectionList()
        viewModel.getCollections()
        observeCollections()
    }

    private fun observeCollections() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.collectionList.collectLatest(collectionAdapter::submitData)
            }
        }

        collectionAdapter.addLoadStateListener { loadState ->
            viewBinding.collectionListView.isVisible = loadState.refresh != LoadState.Loading
            viewBinding.imagesLoginProgress.isVisible = loadState.refresh == LoadState.Loading
        }
    }

    private fun initCollectionList() {
        with(viewBinding.collectionListView) {
            adapter = collectionAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun navigateToDetailInfo(collectionItem: CollectionUiModel) {
        findNavController().navigate(
            CollectionListFragmentDirections.actionCollectionsFragmentToCollectionDetailFragment(collectionItem)
        )
    }
}