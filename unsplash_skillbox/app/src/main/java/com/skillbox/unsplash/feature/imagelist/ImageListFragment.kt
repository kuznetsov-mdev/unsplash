package com.skillbox.unsplash.feature.imagelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentImagesBinding
import com.skillbox.unsplash.feature.imagelist.adapter.ImageItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageListFragment : Fragment(R.layout.fragment_images) {
    private val viewBinding: FragmentImagesBinding by viewBinding()
    private val viewModel: ImageListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        with(viewBinding.imagesList) {
            adapter = ImageItemAdapter(viewModel.getImages())
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            val vDividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(vDividerItemDecoration)
        }
    }
}