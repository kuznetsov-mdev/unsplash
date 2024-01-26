package com.skillbox.unsplash.feature.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.common.extensions.launchAndCollectIn
import com.skillbox.unsplash.databinding.FragmentAuthBinding
import com.skillbox.unsplash.util.toast
import dagger.hilt.android.AndroidEntryPoint

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
//            viewModel.checkConnection()
            viewModel.openLoginPage()
        }

//        viewModel.connectionLiveData.observe(viewLifecycleOwner) {
//            if (it >= 400) {
//                Toast.makeText(requireContext(), "Error 403!!!!", Toast.LENGTH_SHORT).show()
//            } else if (it != -1) {
//                viewModel.createOpenLoginPageIntent()
//            }
//        }

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