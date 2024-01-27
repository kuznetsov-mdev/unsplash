package com.skillbox.unsplash.feature.profile.nav

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.skillbox.unsplash.R
import com.skillbox.unsplash.feature.profile.adapter.ProfileAdapter.Companion.USERNAME_PARAM

class CollectionsNavContainerFragment(private val userName: String) : Fragment(R.layout.fragment_collections_nav_container) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = (childFragmentManager.findFragmentById(R.id.userCollectionNavContainer) as NavHostFragment).navController

        navController.setGraph(
            R.navigation.collections_nav_graph,
            bundleOf(USERNAME_PARAM to userName)
        )
    }
}