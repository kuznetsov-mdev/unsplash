package com.skillbox.unsplash.feature

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.common.network.ConnectivityStatus
import com.skillbox.unsplash.databinding.FragmentAppBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppFragment : Fragment(R.layout.fragment_app) {
    private val binding by viewBinding(FragmentAppBinding::class.java)
    private val viewModel: AppViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.connectivityStateFlow.collectLatest { status ->
                    if (status.name == (ConnectivityStatus.Available.name)) {
                        binding.offlineModeView.visibility = View.GONE
                    } else {
                        binding.offlineModeView.visibility = View.VISIBLE
                    }
                }
            }
        }

        val bottomNavView = binding.appBottomNavigation
        val navController = (childFragmentManager.findFragmentById(R.id.appContainerView) as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottomNavView, navController)
    }
}