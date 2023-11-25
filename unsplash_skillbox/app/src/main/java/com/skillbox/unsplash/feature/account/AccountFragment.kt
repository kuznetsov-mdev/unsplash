package com.skillbox.unsplash.feature.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.FragmentAccountBinding
import com.skillbox.unsplash.feature.account.adapter.AccountAdapter

class AccountFragment : Fragment(R.layout.fragment_account) {
    private val viewBinding: FragmentAccountBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() {
        val pagerAdapter = AccountAdapter(requireActivity())
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
}