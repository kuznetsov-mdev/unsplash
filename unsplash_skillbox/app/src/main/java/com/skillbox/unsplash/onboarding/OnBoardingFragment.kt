package com.skillbox.unsplash.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentOnBoardingBinding
import com.skillbox.unsplash.onboarding.adapter.OnBoardingAdapter
import com.skillbox.unsplash.onboarding.viewmodel.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()
    private val binding: FragmentOnBoardingBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = OnBoardingAdapter(onBoardingViewModel.getScreens(), this)
        binding.onBoardingViewPager.adapter = adapter
        binding.wormDotsIndicator.attachTo(binding.onBoardingViewPager)

        binding.onBoardingViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == onBoardingViewModel.getScreens().size - 1) {
                        binding.onBoardingSkipButton.visibility = View.INVISIBLE
                        binding.onboardingGetStarted.visibility = View.VISIBLE
                    } else {
                        binding.onboardingGetStarted.visibility = View.INVISIBLE
                        binding.onBoardingSkipButton.visibility = View.VISIBLE
                    }
                }
            }
        )

        binding.onBoardingSkipButton.setOnClickListener { navigateToLoginScreen() }
        binding.onboardingGetStarted.setOnClickListener { navigateToLoginScreen() }
    }

    private fun navigateToLoginScreen() {
        lifecycleScope.launch(Dispatchers.IO){
            onBoardingViewModel.setOnBoardingCompletedStatus(requireContext(), true)
        }

        val action = OnBoardingFragmentDirections.actionMainFragmentToAuthFragment()
        findNavController().navigate(action)
    }
}