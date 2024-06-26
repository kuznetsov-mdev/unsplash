package com.skillbox.unsplash.presentation.images.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.StartNavGraphDirections
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import com.skillbox.unsplash.databinding.FragmentImagesBinding
import com.skillbox.unsplash.databinding.LayoutSearchBinding
import com.skillbox.unsplash.domain.model.UnsplashSearchQuery
import com.skillbox.unsplash.presentation.images.list.adapter.ImageAdapter
import com.skillbox.unsplash.util.findTopNavController
import com.skillbox.unsplash.util.textChangedFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListFragment : Fragment(R.layout.fragment_images) {
    private val viewModel: ImageListViewModel by viewModels()
    private val arguments: ImageListFragmentArgs by navArgs()
    private val viewBinding: FragmentImagesBinding by viewBinding()
    private val searchViewBinding: LayoutSearchBinding by viewBinding()
    private var isNetworkAvailableState = true
    private val imageAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ImageAdapter(
            ::markPhoto,
            ::isNetworkAvailable,
            ::onImageClicked
        )
    }

    //https://stackoverflow.com/questions/9727173/support-fragmentpageradapter-holds-reference-to-old-fragments
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViewAdapter()
        initSearchBar()
        observeData()
        getImages()
    }

    private fun getImages(searchImageQuery: String? = null) {
        val userName = arguments.userName
        val onlyLikedPhoto = arguments.likedByUser
        viewModel.getImages(UnsplashSearchQuery(searchImageQuery, userName, onlyLikedPhoto))
    }

    private fun initRecyclerViewAdapter() {
        with(viewBinding.imagesList) {
            adapter = imageAdapter
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val verticalDividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            val horizontalDividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
            addItemDecoration(verticalDividerItemDecoration)
            addItemDecoration(horizontalDividerItemDecoration)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun initSearchBar() {
        searchViewBinding.searchIconView.setOnClickListener {
            searchViewBinding.searchIconView.visibility = View.GONE
            searchViewBinding.searchBarView.visibility = View.VISIBLE
        }

        searchViewBinding.searchBarView.setEndIconOnClickListener {
            searchViewBinding.searchInputTextView.text?.let {
                if (it.isNotBlank()) {
                    searchViewBinding.searchInputTextView.setText("")
                    getImages()
                }
            }
            searchViewBinding.searchIconView.visibility = View.VISIBLE
            searchViewBinding.searchBarView.visibility = View.GONE
        }

        searchViewBinding.searchBarView.setStartIconOnClickListener {
            searchViewBinding.searchInputTextView.text?.let {
                if (it.isNotBlank()) {
                    searchViewBinding.searchInputTextView.setText(R.string.empty)
                    getImages()
                }
            }
        }

        searchViewBinding.searchInputTextView.textChangedFlow()
            .debounce(700)
            .onStart { emit("") }
            .distinctUntilChanged()
            .mapLatest { text -> getImages(text) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun markPhoto(imageId: String, imagePosition: Int, isLiked: Boolean) {
        if (isLiked) {
            viewModel.setLike(imageId)
        } else {
            viewModel.removeLike(imageId)
        }
        imageAdapter.notifyItemChanged(imagePosition)
    }

    private fun isNetworkAvailable(): Boolean = isNetworkAvailableState

    private fun onImageClicked(imageId: String) {
        val action = StartNavGraphDirections.actionGlobalImageDetailFragment(imageId)
        findTopNavController().navigate(action)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imageList.collectLatest(imageAdapter::submitData)
                viewModel.connectivityStateFlow.collectLatest {
                    isNetworkAvailableState = it.name == ConnectivityStatus.Available.name
                }
            }
        }

        imageAdapter.addLoadStateListener { state: CombinedLoadStates ->
            val isLoading = state.refresh == LoadState.Loading
            val isThereAnyData = imageAdapter.itemCount != 0

            viewBinding.imagesLoginProgress.isVisible = isLoading
            viewBinding.noDataImageView.isVisible = !isLoading && !isThereAnyData
            viewBinding.imagesList.isVisible = !isLoading && isThereAnyData
        }
    }

    companion object {
        const val IMAGE_ID_KEY = "imageId"
    }
}