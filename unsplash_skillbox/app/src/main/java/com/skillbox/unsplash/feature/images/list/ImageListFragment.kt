package com.skillbox.unsplash.feature.images.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentImagesBinding
import com.skillbox.unsplash.feature.images.list.adapter.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ImageListFragment : Fragment(R.layout.fragment_images) {
    private val viewBinding: FragmentImagesBinding by viewBinding()
    private val viewModel: ImageListViewModel by viewModels()
    private val imageAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ImageAdapter(
            requireContext(),
            ::markPhoto,
            ::isNetworkAvailable,
            ::onImageClicked
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initSearchBar()
        observeImages()
    }

    private fun initList() {
        with(viewBinding.imagesList) {
            adapter = imageAdapter
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            val verticalDividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            val horizontalDividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
            addItemDecoration(verticalDividerItemDecoration)
            addItemDecoration(horizontalDividerItemDecoration)
        }
    }

    private fun initSearchBar() {
        viewBinding.searchIconView.setOnClickListener {
            viewBinding.searchIconView.visibility = View.GONE
            viewBinding.searchBarView.visibility = View.VISIBLE
        }

        viewBinding.searchBarView.setEndIconOnClickListener {
            viewBinding.searchIconView.visibility = View.VISIBLE
            viewBinding.searchBarView.visibility = View.GONE
            viewBinding.searchInputTextView.setText("")
            viewModel.searchImages(null)
        }

        viewBinding.searchBarView.setStartIconOnClickListener {
            viewBinding.searchInputTextView.setText(R.string.empty)
            viewModel.searchImages(null)
        }

        viewBinding.searchInputTextView.doOnTextChanged { text, start, before, count ->
            Timber.tag("SEARCH - text").d(text.toString())
            viewModel.searchImages(text.toString())
        }
    }

    private fun markPhoto(imageId: String, imagePosition: Int, isLiked: Boolean) {
        if (isLiked) {
            viewModel.setLike(imageId)
        } else {
            viewModel.removeLike(imageId)
        }
        imageAdapter.notifyItemChanged(imagePosition)
    }

    private fun isNetworkAvailable(): Boolean = viewModel.isNetworkAvailableState

    private fun onImageClicked(imageId: String) {
        findNavController().navigate(
            ImageListFragmentDirections.actionImagesFragmentToImageFragment(imageId)
        )
    }

    private fun observeImages() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.imageList.collectLatest(imageAdapter::submitData)
        }

        imageAdapter.addLoadStateListener { state: CombinedLoadStates ->
            viewBinding.imagesList.isVisible = state.refresh != LoadState.Loading
            viewBinding.imagesLoginProgress.isVisible = state.refresh == LoadState.Loading
        }
    }
}