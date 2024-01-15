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
import com.skillbox.unsplash.feature.profile.model.ResponseResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val viewBinding: FragmentProfileBinding by viewBinding()
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.getProfileInfo()
    }

    private fun initViewPager(profile: ProfileUiModel) {
        val pagerAdapter = ProfileAdapter(
            requireActivity(),
            profile.nickname,
        )

        with(viewBinding) {
            viewPager.adapter = pagerAdapter
        }
    }

    private fun getTabName(tabPosition: Int): String {
        return when (tabPosition) {
            0 -> getString(R.string.photos).lowercase()
            1 -> getString(R.string.liked).lowercase()
            2 -> getString(R.string.collections).lowercase()
            else -> throw Error("Unreached tab index")
        }
    }

    private fun getTabCountValue(tabPosition: Int, profile: ProfileUiModel): Int {
        return when (tabPosition) {
            0 -> profile.totalPhotos
            1 -> profile.totalLikes
            2 -> profile.totalCollections
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
            viewModel.profileInfoSharedFlow.collectLatest { profile ->
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    profile.let {
                        when (it) {
                            is ResponseResult.Content -> bindUserProfileData(it.content)
                            is ResponseResult.Error -> showErrorMessage(it.throwable)
                            is ResponseResult.Empty -> showPreloader()
                        }
                    }
                }
            }
        }
    }

    private fun bindUserProfileData(profile: ProfileUiModel) {
        initViewPager(profile)
        with(viewBinding) {

            TabLayoutMediator(tabsLayout, viewPager) { tab, position ->
                tab.text = "${getTabCountValue(position, profile)} \n ${getTabName(position)}".lowercase()
            }.attach()

            userInfoLayout.visibility = View.VISIBLE
            profileProgress.visibility = View.GONE

            uploadImageToView(avatarImageView, profile.profileImage)
            userNameTextView.text = profile.userName
            userNicknameTextView.text = profile.nickname
            biographyTextView.text = profile.bio
            locationTextView.text = profile.location
            emailTextView.text = profile.email
            downloadCountTextView.text = profile.downloads.toString()
        }
    }

    private fun showErrorMessage(t: Throwable) {
        with(viewBinding) {
            val errorText = if (t is HttpException && t.code() == 403) {
                "Тестовая верси приложения. Вы привысили допустимое количество запросов в час"
            } else {
                "При запросе данных пользователя произошла ошибка"
            }
            profileProgress.visibility = View.GONE
            errorMessageLayout.errorText.text = errorText
            errorMessageLayout.root.visibility = View.VISIBLE
            userInfoLayout.visibility = View.GONE
            tabsLayout.visibility = View.GONE
            viewPager.visibility = View.GONE
        }
    }

    private fun showPreloader() {
        with(viewBinding) {
            userInfoLayout.visibility = View.GONE
            profileProgress.visibility = View.VISIBLE
        }
    }
}