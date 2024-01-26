package com.skillbox.unsplash.feature.collections.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.common.network.ConnectivityStatus
import com.skillbox.unsplash.databinding.FragmentCollectionsBinding
import com.skillbox.unsplash.feature.collections.list.adapter.CollectionAdapter
import com.skillbox.unsplash.feature.collections.model.CollectionUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CollectionListFragment : Fragment(R.layout.fragment_collections) {
    private val args: CollectionListFragmentArgs by navArgs()
    private val viewBinding: FragmentCollectionsBinding by viewBinding()
    private val viewModel: CollectionListViewModel by viewModels()
    private var isNetworkAvailableState = true
    private var userName = "none"
    private val collectionAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CollectionAdapter(
            ::navigateToDetailInfo,
            ::isNetworkAvailable
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCollectionList()
        observeData()
        getCollections()
        userName = arguments?.getString(USER_NAME_KEY) ?: userName
        Timber.tag("LifecycleLog").d("${this.javaClass.simpleName} -> onViewCreated user = $userName hash = ${this.hashCode()}")
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.collectionList.collectLatest(collectionAdapter::submitData)
                viewModel.connectivityStateFlow.collectLatest {
                    isNetworkAvailableState = it.name == ConnectivityStatus.Available.name
                }
            }
        }

        collectionAdapter.addLoadStateListener { loadState ->
            val isLoading = loadState.refresh == LoadState.Loading
            val isThereAnyData = collectionAdapter.itemCount != 0

            viewBinding.imagesLoginProgress.isVisible = isLoading
            viewBinding.noDataImageView.isVisible = !isLoading && !isThereAnyData
            viewBinding.collectionListView.isVisible = !isLoading && isThereAnyData
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
        val username = arguments?.getString(USER_NAME_KEY)

        val bundle = bundleOf(
            COLLECTION_ITEM_KEY to collectionItem,
            USER_NAME_KEY to username
        )
        findNavController().navigate(R.id.collectionDetailFragment2, bundle)
    }

    private fun isNetworkAvailable(): Boolean = isNetworkAvailableState

    private fun getCollections() {
        val userName = arguments?.getString(USER_NAME_KEY)
        viewModel.getCollections(userName)
    }

    companion object {
        const val USER_NAME_KEY = "userName"
        const val COLLECTION_ITEM_KEY = "collectionItem"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.tag("LifecycleLog").d("${this.javaClass.simpleName} -> onAttach hash = ${this.hashCode()}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("LifecycleLog").d("${this.javaClass.simpleName} -> onCreate hash = ${this.hashCode()}")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag("LifecycleLog").d("${this.javaClass.simpleName} -> onStart hash = ${this.hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag("LifecycleLog").d("${this.javaClass.simpleName} -> onResume user = $userName hash = ${this.hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag("LifecycleLog").d("${this.javaClass.simpleName} -> onPause user = $userName hash = ${this.hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag("LifecycleLog").d("${this.javaClass.simpleName} -> onStop user = $userName hash = ${this.hashCode()}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.tag("LifecycleLog").d("${this.javaClass.simpleName} -> onDestroyView user = $userName hash = ${this.hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag("LifecycleLog").d("${this.javaClass.simpleName} -> onDestroy user = $userName hash = ${this.hashCode()}")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.tag("LifecycleLog").d("${this.javaClass.simpleName} -> onDetach user = $userName hash = ${this.hashCode()}")
    }
}