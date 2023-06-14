package com.skillbox.unsplash.feature

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentAppBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppFragment : Fragment(R.layout.fragment_app) {
    private val binding by viewBinding(FragmentAppBinding::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bottomNavView = binding.appBottomNavigation
        val navController = (childFragmentManager.findFragmentById(R.id.appContainerView) as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottomNavView, navController)
    }
}