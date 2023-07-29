package com.skillbox.unsplash.feature.imagelist

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentImagesBinding
import com.skillbox.unsplash.feature.imagelist.adapter.ImageAdapter
import com.skillbox.unsplash.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListFragment : Fragment(R.layout.fragment_images) {
    private val viewBinding: FragmentImagesBinding by viewBinding()
    private val viewModel: ImageListViewModel by viewModels()
    private var imageAdapter: ImageAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeImages()
    }

    private fun initList() {
        imageAdapter = ImageAdapter(::markPhoto)
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

    private fun markPhoto(imageId: String, isLiked: Boolean, callback: () -> Unit) {
        if (isLiked) {
            viewModel.setLike(imageId) { callback() }
        } else {
            viewModel.removeLike(imageId) { callback() }
        }
    }

    private fun observeImages() {
        lifecycleScope.launch {
            viewModel.imageList.collectLatest { pagingData ->
                imageAdapter.submitData(pagingData)
            }
        }

        imageAdapter.addLoadStateListener { state: CombinedLoadStates ->
            viewBinding.imagesList.isVisible = state.refresh != LoadState.Loading
            viewBinding.imagesLoginProgress.isVisible = state.refresh == LoadState.Loading
        }
    }
}