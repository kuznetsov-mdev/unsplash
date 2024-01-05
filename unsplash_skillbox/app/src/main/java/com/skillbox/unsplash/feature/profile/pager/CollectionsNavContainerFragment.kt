package com.skillbox.unsplash.feature.profile.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.skillbox.unsplash.R

class CollectionsNavContainerFragment : Fragment(R.layout.fragment_collections_nav_container) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = (childFragmentManager.findFragmentById(R.id.collectionNavContainer) as NavHostFragment).navController
        navController.navigate(R.id.collections_nav_graph, arguments)
    }
}