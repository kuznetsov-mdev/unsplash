package com.skillbox.unsplash.presentation.collections.detail

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
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.skillbox.unsplash.R
import com.skillbox.unsplash.StartNavGraphDirections
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import com.skillbox.unsplash.databinding.FragmentCollectionDetailBinding
import com.skillbox.unsplash.presentation.images.list.adapter.ImageAdapter
import com.skillbox.unsplash.util.findTopNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollectionDetailFragment : Fragment(R.layout.fragment_collection_detail) {
    private val collectionArgs: CollectionDetailFragmentArgs by navArgs()
    private val binding: FragmentCollectionDetailBinding by viewBinding()
    private val viewModel: CollectionDetailViewModel by viewModels()
    private var isNetworkAvailableState = true

    private val collectionImageAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ImageAdapter(
            ::markPhoto,
            ::isNetworkAvailable,
            ::onImageClicked
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isNetworkAvailableState) {
            super.onViewCreated(view, savedInstanceState)
            bindCollectionImagesHeader()
            initCollectionImagesList()
            observeImages()
            viewModel.searchImages(collectionArgs.collectionItem.id)
        } else {
            binding.imageCollectionsList.isVisible = isNetworkAvailableState
            binding.dataIsNotAvailableMsg.isVisible = !isNetworkAvailableState
        }
    }

    private fun initCollectionImagesList() {
        with(binding.imageCollectionsList) {
            adapter = collectionImageAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun bindCollectionImagesHeader() {
        val collectionItem = collectionArgs.collectionItem
        with(binding.collectionTitleView) {
            Glide
                .with(requireContext())
                .load(collectionItem.user.cachedAvatarLocation)
                .centerCrop()
                .placeholder(R.drawable.ic_user_account)
                .into(avatarImageView)

            textAuthorName.text = collectionItem.user.name
            textCollectionHeaderTitle.text = collectionItem.title
            textTotalPhoto.text = collectionItem.count.toString()
        }
    }

    private fun markPhoto(imageId: String, imagePosition: Int, isLiked: Boolean) {
//        if (isLiked) {
//            viewModel.setLike(imageId)
//        } else {
//            viewModel.removeLike(imageId)
//        }
//        collectionImageAdapter.notifyItemChanged(imagePosition)
    }

    private fun isNetworkAvailable(): Boolean = isNetworkAvailableState

    private fun onImageClicked(imageId: String) {
        val action = StartNavGraphDirections.actionGlobalImageDetailFragment(imageId)
        findTopNavController().navigate(action)
    }

    private fun observeImages() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.collectionImageList.collectLatest(collectionImageAdapter::submitData)

                viewModel.connectivityStateFlow.collectLatest {
                    isNetworkAvailableState = it.name == ConnectivityStatus.Available.name
                }
            }
        }

        collectionImageAdapter.addLoadStateListener { state: CombinedLoadStates ->
            binding.imageCollectionsList.isVisible = state.refresh != LoadState.Loading
            binding.imagesLoginProgress.isVisible = state.refresh == LoadState.Loading
        }
    }

}