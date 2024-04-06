package com.skillbox.unsplash.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.unsplash.common.network.ConnectivityStatus
import com.skillbox.unsplash.common.network.api.ConnectivityObserver
import com.skillbox.unsplash.data.profile.ProfileRepository
import com.skillbox.unsplash.domain.model.local.ProfileUiModel
import com.skillbox.unsplash.domain.model.local.ResponseResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = connectivityObserver.observe()

    private val profileInfoMutableSharedFlow: MutableSharedFlow<ResponseResultType<ProfileUiModel>> = MutableSharedFlow()

    val profileInfoSharedFlow: SharedFlow<ResponseResultType<ProfileUiModel>>
        get() = profileInfoMutableSharedFlow.asSharedFlow()

    fun getProfileInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            profileInfoMutableSharedFlow.emit(profileRepository.getInfo())
        }
    }
}