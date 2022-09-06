package com.skillbox.unsplash.onboarding.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.skillbox.unsplash.R
import com.skillbox.unsplash.onboarding.repository.OnBoardingRepository

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val onboardingRepository = OnBoardingRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launchWhenCreated {
            val isOnboardingCompleted = onboardingRepository.isOnboardingCompleted(requireContext())
            Handler(Looper.getMainLooper()).postDelayed({
                if(!isOnboardingCompleted) {
                    findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_authFragment)
                }

            }, SPLASH_SCREEN_DISPLAYING_DURATION)
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        private const val SPLASH_SCREEN_DISPLAYING_DURATION = 2_000L
    }
}