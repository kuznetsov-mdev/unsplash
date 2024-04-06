package com.skillbox.unsplash.presentation.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skillbox.unsplash.data.onboarding.model.OnBoardingScreenModel
import com.skillbox.unsplash.presentation.onboarding.item.OnBoardingItemFragment

class OnBoardingAdapter(
    private val screens: List<OnBoardingScreenModel>,
    activity: Fragment
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return screens.size
    }

    override fun createFragment(position: Int): Fragment {
        val screen: OnBoardingScreenModel = screens[position]
        return OnBoardingItemFragment.newInstance(
            screen.drawableRes,
            screen.textRes
        )
    }
}