package com.skillbox.unsplash.presentation.onboarding.item

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.ItemOnBoardingBinding
import com.skillbox.unsplash.util.withArguments

class OnBoardingItemFragment : Fragment(R.layout.item_on_boarding) {
    private val binding: ItemOnBoardingBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onBoardingImgView.setImageResource(requireArguments().getInt(KEY_IMAGE))
        binding.onBoardingTextView.setText(requireArguments().getInt(KEY_TEXT))
    }

    companion object {
        private const val KEY_TEXT = "onboarding_text"
        private const val KEY_IMAGE = "onboarding_image"

        fun newInstance(
            @DrawableRes drawableRes: Int,
            @StringRes textRes: Int
        ): OnBoardingItemFragment {
            return OnBoardingItemFragment().withArguments {
                putInt(KEY_IMAGE, drawableRes)
                putInt(KEY_TEXT, textRes)
            }
        }
    }


}

