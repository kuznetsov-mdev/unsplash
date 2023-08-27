package com.skillbox.unsplash.feature.images.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentImageBinding
import com.skillbox.unsplash.databinding.ImageLayoutCameraInfoBinding
import com.skillbox.unsplash.databinding.ImageLayoutImageStatisticBinding
import com.skillbox.unsplash.feature.images.detail.data.DetailImageItem
import com.skillbox.unsplash.feature.images.detail.data.Exif
import com.skillbox.unsplash.feature.images.detail.data.Statistic
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailImageFragment : Fragment(R.layout.fragment_image) {
    private val args: DetailImageFragmentArgs by navArgs()
    private val viewModel: DetailImageViewModel by viewModels()
    private val imageDetailBinding: FragmentImageBinding by viewBinding()
    private val cameraInfoBinding: ImageLayoutCameraInfoBinding by viewBinding()
    private val imageStatisticBinding: ImageLayoutImageStatisticBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeImageDetailInfo()
        observeDataLoading()
        viewModel.getImageDetailInfo(args.imageId)
    }

    private fun observeDataLoading() {
        lifecycleScope.launch {
            viewModel.isDataLoadingFlow.collectLatest { isDataLoading ->
                imageDetailBinding.imageLoadingProgress.isVisible = isDataLoading
                imageDetailBinding.contentLayout.isVisible = !isDataLoading
            }
        }
    }

    private fun observeImageDetailInfo() {
        lifecycleScope.launch {
            viewModel.imageDetailFlow.collectLatest { detailImgItem: DetailImageItem? ->
                detailImgItem?.let {
                    bindImageDetail(detailImgItem)
                    bindCameraInfo(detailImgItem.exif)
                    bindStatisticInfo(detailImgItem.statistic)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindImageDetail(detailImgItem: DetailImageItem) {
        with(imageDetailBinding) {
            Glide.with(requireContext())
                .load(detailImgItem.image.url)
                .placeholder(R.drawable.ic_img_placeholder_foreground)
                .into(imageItemView)

            Glide.with(requireContext())
                .load(detailImgItem.author.avatarUrl)
                .placeholder(R.drawable.ic_img_placeholder_foreground)
                .into(avatarImageView)

            authorName.text = detailImgItem.author.name
            authorNickName.text = detailImgItem.author.nickname
            activeLikesIconView.isVisible = detailImgItem.image.likedByUser
            inactiveLikesIconView.isVisible = !detailImgItem.image.likedByUser

            if (detailImgItem.location.latitude == 0.0 && detailImgItem.location.longitude == 0.0) {
                locationDescView.text = "Unknown"
            } else {
                locationDescView.text = "${detailImgItem.location.city} ${detailImgItem.location.country}"
            }
            aboutAuthorNickname.text = "@${detailImgItem.author.nickname} ${detailImgItem.author.biography}"

            cameraInfoBinding.dimensionsValue.text = "${detailImgItem.width} x ${detailImgItem.height}"

            tagsTextValue.text = detailImgItem.tags.map { "#$it" }.toString()

            activeLikesIconView.setOnClickListener {
                if (viewModel.isNetworkAvailableState) {
                    viewModel.removeLike(detailImgItem.image.id)
                    activeLikesIconView.isVisible = false
                    inactiveLikesIconView.isVisible = true
                }
            }

            inactiveLikesIconView.setOnClickListener {
                if (viewModel.isNetworkAvailableState) {
                    viewModel.setLike(detailImgItem.image.id)
                    activeLikesIconView.isVisible = true
                    inactiveLikesIconView.isVisible = false
                }
            }
        }
    }

    private fun bindCameraInfo(cameraInfo: Exif) {
        with(cameraInfoBinding) {
            cameraValue.text = cameraInfo.name
            focalLengthValue.text = cameraInfo.focalLength
            isoValue.text = cameraInfo.iso.toString()
            apertureValue.text = cameraInfo.aperture
            shutterSpeedValue.text = cameraInfo.exposureTime
        }
    }

    private fun bindStatisticInfo(statistics: Statistic) {
        with(imageStatisticBinding) {
            viewsTextValue.text = statistics.views.toString()
            downloadsTextValue.text = statistics.downloads.toString()
            likesTextValue.text = statistics.likes.toString()
        }
    }

}