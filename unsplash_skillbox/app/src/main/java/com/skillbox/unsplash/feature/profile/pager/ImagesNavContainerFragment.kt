package com.skillbox.unsplash.feature.profile.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.skillbox.unsplash.R

class ImagesNavContainerFragment : Fragment(R.layout.fragment_images_nav_container) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = (childFragmentManager.findFragmentById(R.id.imagesNavContainer) as NavHostFragment).navController
        navController.setGraph(R.navigation.images_nav_graph, arguments)
    }
}