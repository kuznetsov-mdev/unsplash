package com.skillbox.unsplash.feature.profile

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentProfileBinding
import com.skillbox.unsplash.feature.profile.adapter.ProfileAdapter
import com.skillbox.unsplash.feature.profile.model.ProfileUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val viewBinding: FragmentProfileBinding by viewBinding()
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        observeData()
        viewModel.getAccountInfo()
    }

    private fun initViewPager() {
        val pagerAdapter = ProfileAdapter(requireActivity())
        with(viewBinding) {
            viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabsLayout, viewPager) { tab, position ->
                tab.text = getString(getTabName(position))
            }.attach()
        }

    }

    private fun getTabName(tabPosition: Int): Int {
        return when (tabPosition) {
            0 -> R.string.photos
            1 -> R.string.liked
            2 -> R.string.collections
            else -> throw Error("Unreached tab index")
        }
    }

    private fun uploadImageToView(view: ImageView, imageUrl: String) {
        Glide.with(requireContext())
            .load(imageUrl)
            .placeholder(R.drawable.ic_img_placeholder_foreground)
            .into(view)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileStateFlow.collectLatest { profile ->
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    profile?.let {
                        bindUserProfileData(it)
                    }
                }
            }
        }
    }

    private fun bindUserProfileData(profile: ProfileUiModel) {
        with(viewBinding) {
            uploadImageToView(avatarImageView, profile.profileImage)
            userNameTextView.text = profile.userName
            userNicknameTextView.text = profile.nickname
            biographyTextView.text = profile.bio
            locationTextView.text = profile.location
            emailTextView.text = profile.email
            downloadCountTextView.text = profile.downloads.toString()
        }


    }
}