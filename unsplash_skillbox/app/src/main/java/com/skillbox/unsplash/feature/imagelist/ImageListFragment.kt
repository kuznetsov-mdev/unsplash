package com.skillbox.unsplash.feature.imagelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skillbox.unsplash.R

class ImageListFragment : Fragment(R.layout.fragment_images) {
    private val viewModel: ImageListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}