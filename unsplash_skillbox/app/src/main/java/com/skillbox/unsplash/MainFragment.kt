package com.skillbox.unsplash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.databinding.FragmentMainBinding
import com.skillbox.unsplash.onboarding.adapter.OnBoardingAdapter
import com.skillbox.unsplash.onboarding.repository.OnBoardingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {
    private val onBoardingRepository = OnBoardingRepository()
    private val binding: FragmentMainBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = OnBoardingAdapter(onBoardingRepository.getScreens(), this)
        binding.onboardingViewPager.adapter = adapter
        binding.wormDotsIndicator.attachTo(binding.onboardingViewPager)

        binding.onboardingViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == onBoardingRepository.getScreens().size - 1) {
                        binding.onboardingSkipButton.visibility = View.INVISIBLE
                        binding.onboardingGetStarted.visibility = View.VISIBLE
                    } else {
                        binding.onboardingGetStarted.visibility = View.INVISIBLE
                        binding.onboardingSkipButton.visibility = View.VISIBLE
                    }
                }
            }
        )

        binding.onboardingSkipButton.setOnClickListener { navigateToLoginScreen() }
        binding.onboardingGetStarted.setOnClickListener { navigateToLoginScreen() }
    }

    private fun navigateToLoginScreen() {
        lifecycleScope.launch(Dispatchers.IO){
            onBoardingRepository.setOnboardingCompletedStatus(requireContext(), true)
        }

        val action = MainFragmentDirections.actionMainFragmentToAuthFragment()
        findNavController().navigate(action)
    }
}