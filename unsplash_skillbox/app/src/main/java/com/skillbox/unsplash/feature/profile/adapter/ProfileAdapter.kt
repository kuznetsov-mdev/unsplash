package com.skillbox.unsplash.feature.profile.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skillbox.unsplash.feature.profile.model.ProfileUiModel
import com.skillbox.unsplash.feature.profile.pager.CollectionsNavContainerFragment
import com.skillbox.unsplash.feature.profile.pager.ImagesNavContainerFragment


class ProfileAdapter(
    fragment: FragmentActivity,
    private val profile: ProfileUiModel
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return TABS_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> getUserImagesFragment()
            1 -> getLikedImagesFragment()
            2 -> getUserCollectionsFragment()
            else -> throw Error("Not implemented yet")
        }
    }

    private fun getUserImagesFragment(): Fragment {
        return ImagesNavContainerFragment().apply {
            arguments = Bundle().apply { putString(USERNAME_PARAM, profile.nickname) }
        }
    }

    private fun getLikedImagesFragment(): Fragment {
        return ImagesNavContainerFragment().apply {
            arguments = Bundle().apply {
                putString(USERNAME_PARAM, profile.nickname)
                putBoolean(LIKED_BY_USER, true)
            }
        }
    }

    private fun getUserCollectionsFragment(): Fragment {
        return CollectionsNavContainerFragment().apply {
            arguments = Bundle().apply { putString(USERNAME_PARAM, profile.nickname) }
        }
    }

    companion object {
        private const val TABS_COUNT = 3
        const val USERNAME_PARAM = "username"
        const val LIKED_BY_USER = "likedByUser"
    }
}