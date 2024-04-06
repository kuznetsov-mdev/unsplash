package com.skillbox.unsplash.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.common.extensions.launchAndCollectIn
import com.skillbox.unsplash.databinding.FragmentAuthBinding
import com.skillbox.unsplash.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {
    private val viewModel: AuthViewModel by viewModels()
    private val binding by viewBinding(FragmentAuthBinding::class.java)

    private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            viewModel.handleAuthResponseIntent(dataIntent)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        binding.loginButton.setOnClickListener {
            binding.loginProgress.isVisible = true
            viewModel.checkConnection()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.connectivityResult.collectLatest { resutl ->
                    binding.rateLimitMsg.isVisible = false
                    if (resutl >= 400) {
                        binding.rateLimitMsg.isVisible = true
                        binding.loginProgress.isVisible = false
                    } else if (resutl != -1) {
                        viewModel.openLoginPage()
                    }
                }
            }
        }

        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) {
            updateIsLoading(it)
        }

        viewModel.openAuthPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            openAuthPage(it)
        }

        viewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            toast(it)
        }

        viewModel.authSuccessFlow.launchAndCollectIn(viewLifecycleOwner) {
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToAppFragment())
        }
    }

    private fun openAuthPage(intent: Intent) {
        activityResultLauncher.launch(intent)
    }

    private fun updateIsLoading(isLoading: Boolean) = with(binding) {
        loginButton.isVisible = !isLoading
        loginProgress.isVisible = isLoading
    }
}