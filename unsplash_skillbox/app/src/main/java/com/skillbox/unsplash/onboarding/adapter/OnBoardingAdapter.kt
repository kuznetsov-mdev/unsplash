package com.skillbox.unsplash.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skillbox.unsplash.onboarding.data.OnBoardingScreen
import com.skillbox.unsplash.onboarding.fragment.OnBoardingItemFragment

class OnBoardingAdapter(
    private val screens: List<OnBoardingScreen>,
    activity: Fragment
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return screens.size
    }

    override fun createFragment(position: Int): Fragment {
        val screen: OnBoardingScreen = screens[position]
        return OnBoardingItemFragment.newInstance(
            screen.drawableRes,
            screen.textRes
        )
    }
}