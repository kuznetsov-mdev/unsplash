package com.skillbox.unsplash.presentation

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.appbar.MaterialToolbar
import com.skillbox.unsplash.R
import com.skillbox.unsplash.common.extensions.launchAndCollectIn
import com.skillbox.unsplash.common.network.ConnectivityStatus
import com.skillbox.unsplash.databinding.FragmentMainBinding
import com.skillbox.unsplash.util.resetNavGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::class.java)
    private val viewModel: MainViewModel by viewModels()
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val logoutResponse: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.webLogoutComplete(it)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTopAppbar()
        initBottomNavMenu()
        observeData()
    }

    private fun initBottomNavMenu() {
        val bottomNavView = binding.mainBottomNavigation
        val navController = (childFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment).navController
        bottomNavView.setupWithNavController(navController)
    }

    private fun initTopAppbar() {
        topAppBar = binding.topAppBar
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.images_nav_graph,
                R.id.collections_nav_graph,
                R.id.profile_nav_graph
            )
        )
        topAppBar.menu.findItem(R.id.share_item).isVisible = false

        topAppBar.menu.findItem(R.id.search_item).setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                return true
            }
        })

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout_top_bar_item -> {
                    Toast.makeText(requireActivity(), "Logout pressed", Toast.LENGTH_LONG).show()
                    viewModel.logout()
                    findNavController().navigate(R.id.authFragment)
                    true
                }
                else -> true
            }
        }

        val navController = (childFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment).navController
        topAppBar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun observeData() {
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

        viewModel.logoutCompletedFlow.launchAndCollectIn(viewLifecycleOwner) {
            findNavController().clearBackStack(R.id.mainFragment)
            findNavController().resetNavGraph(R.navigation.start_nav_graph)
        }

        viewModel.logoutPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            logoutResponse.launch(it)
        }
    }
}