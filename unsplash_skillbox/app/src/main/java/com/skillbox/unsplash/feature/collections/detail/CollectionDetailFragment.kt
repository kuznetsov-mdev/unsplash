package com.skillbox.unsplash.feature.collections.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentCollectionDetailBinding
import com.skillbox.unsplash.feature.images.list.adapter.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionDetailFragment : Fragment(R.layout.fragment_collection_detail) {
    private val collectionArgs: CollectionDetailFragmentArgs by navArgs()
    private val binding: FragmentCollectionDetailBinding by viewBinding()
    private val viewModel: CollectionDetailViewModel by viewModels()

    private val collectionAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ImageAdapter(
            ::markPhoto,
            ::isNetworkAvailable,
            ::onImageClicked
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindCollectionImagesHeader()
        initCollectionImagesList()

    }

    private fun initCollectionImagesList() {
        with(binding.imageCollectionsList) {
            adapter = collectionAdapter
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
            textPublishedAt.text = "stub"
            textUpdatedAt.text = "stub"
        }
    }

    private fun navigateToDetailInfo(imageId: String) {
        findNavController().navigate(
            CollectionDetailFragmentDirections.actionCollectionDetailFragmentToImageFragment(imageId)
        )
    }

    private fun markPhoto(imageId: String, imagePosition: Int, isLiked: Boolean) {
//        if (isLiked) {
//            viewModel.setLike(imageId)
//        } else {
//            viewModel.removeLike(imageId)
//        }
//        imageAdapter.notifyItemChanged(imagePosition)
    }

    private fun isNetworkAvailable(): Boolean = viewModel.isNetworkAvailableState

    private fun onImageClicked(imageId: String) {
        findNavController().navigate(
            CollectionDetailFragmentDirections.actionCollectionDetailFragmentToImageFragment(imageId)
        )
    }

}