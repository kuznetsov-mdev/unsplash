package com.skillbox.unsplash.feature.profile.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skillbox.unsplash.feature.collections.list.CollectionListFragment
import com.skillbox.unsplash.feature.images.list.ImageListFragment
import com.skillbox.unsplash.feature.profile.model.ProfileUiModel


class ProfileAdapter(
    fragment: FragmentActivity,
    private val profile: ProfileUiModel
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return TABS_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        val imageListWithLikedPhotosFragment = ImageListFragment().apply {
            arguments = Bundle().apply { putString(USERNAME_PARAM, profile.nickname) }
        }

        val userImageListFragment = ImageListFragment().apply {
            arguments = Bundle().apply { putString(USERNAME_PARAM, profile.nickname) }
        }

        val userCollectionListFragment = CollectionListFragment().apply {
            arguments = Bundle().apply { putString(USERNAME_PARAM, profile.nickname) }
        }

        return when (position) {
            0 -> imageListWithLikedPhotosFragment
            1 -> userImageListFragment
            else -> userCollectionListFragment
        }
    }

    companion object {
        private const val TABS_COUNT = 3
        const val USERNAME_PARAM = "username"
    }
}