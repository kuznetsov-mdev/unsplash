package com.skillbox.unsplash.feature.collections.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skillbox.unsplash.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionListFragment : Fragment(R.layout.fragment_collections) {
    private val viewModel: CollectionListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}