package com.skillbox.unsplash.feature.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentAccountBinding
import com.skillbox.unsplash.feature.profile.adapter.ProfileAdapter
import com.skillbox.unsplash.feature.profile.model.ProfileUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Suppress("UNUSED_EXPRESSION")
@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_account) {
    private val viewBinding: FragmentAccountBinding by viewBinding()
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        observeData()
        //viewModel.getAccountInfo()
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

    private fun bindData(profileModel: ProfileUiModel) {
        with(viewBinding) {
            authorName.text = profileModel.userName
            authorNickName.text = profileModel.nickname
            emailTextView.text = profileModel.email
        }

    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileStateFlow.collectLatest { this@ProfileFragment::bindData }
            }
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
}