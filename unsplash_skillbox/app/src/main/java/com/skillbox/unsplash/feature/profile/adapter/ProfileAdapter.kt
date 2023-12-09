package com.skillbox.unsplash.feature.profile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skillbox.unsplash.feature.collections.list.CollectionListFragment
import com.skillbox.unsplash.feature.images.list.ImageListFragment

class ProfileAdapter(
    fragment: FragmentActivity,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return TABS_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ImageListFragment()
            1 -> ImageListFragment()
            else -> CollectionListFragment()
        }
    }

    companion object {
        private const val TABS_COUNT = 3;
    }
}