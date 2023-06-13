package com.skillbox.unsplash.feature.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.unsplash.R
import com.skillbox.unsplash.common.extensions.launchAndCollectIn
import com.skillbox.unsplash.databinding.FragmentAuthBinding
import com.skillbox.unsplash.util.toast
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

class AuthFragment : Fragment(R.layout.fragment_auth) {
    private val viewModel: AuthViewModel by viewModels()
    private val binding by viewBinding(FragmentAuthBinding::class.java)

    private val getAuthResponse = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val dataIntent = it.data ?: return@registerForActivityResult
        handleAuthResponseIntent(dataIntent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        binding.loginButton.setOnClickListener { viewModel.openLoginPage() }

        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) {
            updateIsLoading(it)
        }

        viewModel.openAuthPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            openAuthPage(it)
        }

        viewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            toast(it)
        }

        viewModel.authSuccessFlow.launchAndCollectIn(viewLifecycleOwner) {
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToAppFragment())
        }

    }

    private fun openAuthPage(intent: Intent) {
        getAuthResponse.launch(intent)
    }

    private fun handleAuthResponseIntent(intent: Intent) {
        //пытаемся получить ошибку из ответа. null - есди все ок
        val exception = AuthorizationException.fromIntent(intent)
        //пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)?.createTokenExchangeRequest()
        when {
            //авторизация завершилась ошибкой
            exception != null -> viewModel.onAuthCodeFailed(exception)
            //авторизация прошла успешно
            tokenExchangeRequest != null -> viewModel.onAuthCodeReceived(tokenExchangeRequest)
        }
    }

    private fun updateIsLoading(isLoading: Boolean) = with(binding) {
        loginButton.isVisible = !isLoading
        loginProgress.isVisible = isLoading
    }
}