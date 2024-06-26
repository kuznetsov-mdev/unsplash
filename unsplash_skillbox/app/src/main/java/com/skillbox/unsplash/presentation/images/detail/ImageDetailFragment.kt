package com.skillbox.unsplash.presentation.images.detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.work.WorkInfo
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.skillbox.unsplash.R
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import com.skillbox.unsplash.data.service.DownloadWorker
import com.skillbox.unsplash.data.service.NotificationChannels
import com.skillbox.unsplash.databinding.FragmentImageDetailBinding
import com.skillbox.unsplash.databinding.ImageLayoutCameraInfoBinding
import com.skillbox.unsplash.databinding.ImageLayoutImageStatisticBinding
import com.skillbox.unsplash.databinding.ImageLayoutLocationBinding
import com.skillbox.unsplash.domain.model.detail.ExifModel
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import com.skillbox.unsplash.domain.model.detail.LocationModel
import com.skillbox.unsplash.domain.model.detail.StatisticModel
import com.skillbox.unsplash.util.haveQ
import com.skillbox.unsplash.util.haveTiramisu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


typealias ImageDownloader = () -> Unit

@AndroidEntryPoint
class ImageDetailFragment : Fragment(R.layout.fragment_image_detail) {
    private val args: ImageDetailFragmentArgs by navArgs()
    private val viewModel: ImageDetailViewModel by viewModels()
    private val imageDetailBinding: FragmentImageDetailBinding by viewBinding()
    private val cameraInfoBinding: ImageLayoutCameraInfoBinding by viewBinding()
    private val imageStatisticBinding: ImageLayoutImageStatisticBinding by viewBinding()
    private val imageLocationBinding: ImageLayoutLocationBinding by viewBinding()
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var imageDownloader: ImageDownloader
    private var isNetworkAvailableState = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionRequestListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeImageDetailInfo()
        observeDataLoading()
        observePermissionGranted()
        viewModel.getImageDetailInfo(args.imageId)
    }

    private fun observeDataLoading() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isDataLoadingFlow.collectLatest { isDataLoading ->
                    imageDetailBinding.imageLoadingProgress.isVisible = isDataLoading
                    imageDetailBinding.contentLayout.isVisible = !isDataLoading
                }
            }
        }
    }

    private fun observeImageDetailInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imageDetailFlow.collectLatest { detailImgItem: ImageDetailModel? ->
                    detailImgItem?.let {
                        imageDownloader = {
                            showNotification()
                            viewModel.startImageSavingToGalleryWork(args.imageId, detailImgItem.downloadLink)
                                .observe(viewLifecycleOwner, ::handleWorkInfo)
                        }
                        bindImageDetail(detailImgItem)
                        bindCameraInfo(detailImgItem.exif)
                        bindStatisticInfo(detailImgItem.statistic)
                        bindLocation(detailImgItem)
                    }
                }
            }
        }
    }

    private fun handleWorkInfo(workInfo: WorkInfo) {
        Timber.d("Image downloading state is ${workInfo.state.name}")
        if (workInfo.state.isFinished) {
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                showSnackBar(workInfo.outputData.keyValueMap[DownloadWorker.IMAGE_URI_KEY] as String)
            } else {
                showPopUpMessage("${getString(R.string.download_finished_with_status)} ${workInfo.state.name}")
            }

            NotificationManagerCompat.from(requireContext())
                .cancel(DOWNLOAD_NOTIFICATION_ID)
        }
    }

    private fun observePermissionGranted() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.permissionGrantedStateFlow.collectLatest { isGranted ->
                    if (isGranted) {
                        imageDownloader.invoke()
                        Timber.d("Save image to gallery")
                    }
                }

                viewModel.connectivityStateFlow.collectLatest {
                    isNetworkAvailableState = it.name == ConnectivityStatus.Available.name
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindImageDetail(detailImgItem: ImageDetailModel) {
        with(imageDetailBinding) {
            uploadImageToView(imageItemView, detailImgItem.image.url)
            uploadImageToView(avatarImageView, detailImgItem.author.avatarUrl)

            userNameTextView.text = detailImgItem.author.name
            userNicknameTextView.text = detailImgItem.author.nickname
            activeLikesIconView.isVisible = detailImgItem.image.likedByUser
            inactiveLikesIconView.isVisible = !detailImgItem.image.likedByUser
            aboutAuthorNickname.text = "@${detailImgItem.author.nickname} ${detailImgItem.author.biography}"
            cameraInfoBinding.dimensionsValue.text = "${detailImgItem.width} x ${detailImgItem.height}"
            tagsTextValue.text = detailImgItem.tags.map { "#$it" }.toString()

            activeLikesIconView.setOnClickListener {
                if (isNetworkAvailableState) {
                    viewModel.removeLike(detailImgItem.image.id)
                    activeLikesIconView.isVisible = false
                    inactiveLikesIconView.isVisible = true
                }
            }

            inactiveLikesIconView.setOnClickListener {
                if (isNetworkAvailableState) {
                    viewModel.setLike(detailImgItem.image.id)
                    activeLikesIconView.isVisible = true
                    inactiveLikesIconView.isVisible = false
                }
            }

            downloadIcon.setOnClickListener {
                if (hasPermission().not()) {
                    requestPermission()
                } else {
                    imageDownloader.invoke()
                }
            }

            backArrowIcon.setOnClickListener {
                findNavController().popBackStack();
            }

            shareImageIcon.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Image info")
                val shareMessage = UNSPLASH_DEEP_LINK + detailImgItem.image.id
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, getString(R.string.chose_one)))
            }
        }
    }

    private fun uploadImageToView(view: ImageView, imageUrl: String) {
        Glide.with(requireContext())
            .load(imageUrl)
            .placeholder(R.drawable.ic_img_placeholder_foreground)
            .into(view)
    }

    private fun bindCameraInfo(cameraInfo: ExifModel) {
        with(cameraInfoBinding) {
            cameraValue.text = cameraInfo.name
            focalLengthValue.text = cameraInfo.focalLength
            isoValue.text = cameraInfo.iso.toString()
            apertureValue.text = cameraInfo.aperture
            shutterSpeedValue.text = cameraInfo.exposureTime
        }
    }

    private fun bindStatisticInfo(statistics: StatisticModel) {
        with(imageStatisticBinding) {
            viewsTextValue.text = statistics.views.toString()
            downloadsTextValue.text = statistics.downloads.toString()
            likesTextValue.text = statistics.likes.toString()
        }
    }

    private fun bindLocation(detailImgItem: ImageDetailModel) {
        val location: LocationModel = detailImgItem.location;
        val isLocationInfoAbsent = location.country == null && location.city == null && location.name == null
        val isAnyLocationInfoPresent = location.country != null || location.city != null
        val isLocationNamePresent = location.name != null
        val isPositionPresent = location.longitude != null && location.longitude != 0.0
                && location.latitude != null && location.latitude != 0.0

        if (isLocationInfoAbsent) {
            imageDetailBinding.locationLayout.locationView.isVisible = false
        } else if ((isAnyLocationInfoPresent || isLocationNamePresent) && !isPositionPresent) {
            imageLocationBinding.inactiveLocationIconView.isVisible = true
            imageLocationBinding.locationIconView.isVisible = false
            setLocationText(location)
        } else if (isPositionPresent && isAnyLocationInfoPresent) {
            imageLocationBinding.inactiveLocationIconView.isVisible = false
            imageLocationBinding.locationIconView.isVisible = true
            setLocationText(location)

            imageLocationBinding.locationBox.setOnClickListener {
                val uri = Uri.parse("geo:${detailImgItem.location.latitude},${detailImgItem.location.longitude}?z=10")
                sendActionViewIntent(uri, MAPS_INTENT_PACKAGE)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setLocationText(location: LocationModel) {
        if (location.city != null || location.country != null) {
            imageLocationBinding.locationDescView.text = "${location.city ?: ""} ${location.country ?: ""}"
        } else {
            imageLocationBinding.locationDescView.text = location.name
        }
    }

    private fun hasPermission(): Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(PERMISSIONS.toTypedArray())
    }

    private fun initPermissionRequestListener() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionToGrantedMap: Map<String, Boolean> ->
            if (permissionToGrantedMap.values.all { it }) {
                viewModel.permissionGranted()
            } else {
                viewModel.permissionDenied()
            }
        }
    }

    private fun showPopUpMessage(msg: String) {
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showNotification() {
        val downloadNotification = NotificationCompat.Builder(requireContext(), NotificationChannels.DOWNLOAD_CHANNEL_ID)
            .setContentTitle(getString(R.string.image_downloading))
            .setProgress(0, 100, true)
            .setSmallIcon(R.drawable.ic_download_black)
            .build()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(requireContext())
                .notify(DOWNLOAD_NOTIFICATION_ID, downloadNotification)
        } else {
            if (haveTiramisu()) {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
            }
        }
    }

    private fun showSnackBar(imageUri: String) {
        val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.mainBottomNavigation)
        Snackbar.make(imageDetailBinding.contentLayout, R.string.download_is_finished, Snackbar.LENGTH_LONG)
            .setAnchorView(bottomNavView)
            .setAction(getString(R.string.open)) {
                openImage(imageUri)
            }
            .show()
    }

    private fun openImage(stringUri: String) {
        val uri = Uri.parse(stringUri)
        sendActionViewIntent(uri)
    }

    private fun sendActionViewIntent(uri: Uri, intentPackage: String? = null) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intentPackage?.let { intent.setPackage(it) }
        requireContext().startActivity(intent)

        intent.resolveActivity(requireContext().packageManager)?.let {
            startActivity(intent)
        }
    }

    companion object {
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE
                .takeIf { haveTiramisu().not() },
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { haveQ().not() },
            Manifest.permission.READ_MEDIA_IMAGES
                .takeIf { haveTiramisu() }
        )

        private const val DOWNLOAD_NOTIFICATION_ID = 100501
        private const val MAPS_INTENT_PACKAGE = "com.google.android.apps.maps"
        private const val UNSPLASH_DEEP_LINK = "https://unsplash.com/photos/"
    }

}