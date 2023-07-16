package com.skillbox.unsplash.feature.imagelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentImagesBinding
import com.skillbox.unsplash.feature.imagelist.adapter.ImageItemAdapter
import com.skillbox.unsplash.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageListFragment : Fragment(R.layout.fragment_images) {
    private val viewBinding: FragmentImagesBinding by viewBinding()
    private val viewModel: ImageListViewModel by viewModels()
    private var imageListAdapter: ImageItemAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeData()
    }

    private fun initList() {
        imageListAdapter = ImageItemAdapter(::markPhoto)

        with(viewBinding.imagesList) {
            adapter = imageListAdapter
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            val verticalDividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            val horizontalDividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
            addItemDecoration(verticalDividerItemDecoration)
            addItemDecoration(horizontalDividerItemDecoration)
        }
    }

    private fun markPhoto(imageId: String, isLiked: Boolean) {
//        val list = viewModel.getImages().toList()
//        val elem = list.first { it.id == imageId }
//        elem.likedByUser = isLiked;
//        var updatedList = list.filter { it.id != imageId }.toMutableList()
//        updatedList.add(elem)
//        imageListAdapter.setImages(updatedList)
    }

    private fun observeData() {
        viewModel.images.observe(viewLifecycleOwner) { newImages ->
            imageListAdapter.setImages(newImages)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                viewBinding.imagesList.visibility = View.GONE
                viewBinding.imagesLoginProgress.visibility = View.VISIBLE
            } else {
                viewBinding.imagesList.visibility = View.VISIBLE
                viewBinding.imagesLoginProgress.visibility = View.GONE
            }
        }
        viewModel.getImageList()
    }
}