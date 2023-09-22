package com.skillbox.unsplash.feature.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.skillbox.unsplash.R
import com.skillbox.unsplash.feature.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val isOnBoardingCompleted = onBoardingViewModel.isOnBoardingCompleted(requireContext())
                //todo: replace it to checking network request for getting user info
                val isUserLoggedIn = onBoardingViewModel.isUserLoggedIn()
                Handler(Looper.getMainLooper()).postDelayed({
                    if (!isOnBoardingCompleted) {
                        findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                    } else {
                        if (isUserLoggedIn) {
                            findNavController().navigate(R.id.action_splashFragment_to_appFragment)
                        } else {
                            findNavController().navigate(R.id.action_splashFragment_to_authFragment)
                        }
                    }
                }, SPLASH_SCREEN_DISPLAYING_DURATION)
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        private const val SPLASH_SCREEN_DISPLAYING_DURATION = 1_000L
    }
}