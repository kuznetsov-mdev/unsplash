package com.skillbox.unsplash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.databinding.FragmentMainBinding
import com.skillbox.unsplash.onboarding.adapter.OnBoardingAdapter
import com.skillbox.unsplash.onboarding.repository.OnBoardingRepository

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
                        binding.onboardingGetStarte.visibility = View.VISIBLE
                    } else {
                        binding.onboardingGetStarte.visibility = View.INVISIBLE
                    }
                }
            }
        )
    }
}