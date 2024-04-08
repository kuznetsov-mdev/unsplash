package com.skillbox.unsplash.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import com.skillbox.unsplash.domain.model.ProfileModel
import com.skillbox.unsplash.domain.model.ResponseResultType
import com.skillbox.unsplash.domain.usecase.common.GetNetworkStateUseCase
import com.skillbox.unsplash.domain.usecase.profile.GetUserProfileUseCase
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
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {

    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = getNetworkStateUseCase()

    private val profileInfoMutableSharedFlow: MutableSharedFlow<ResponseResultType<ProfileModel>> = MutableSharedFlow()

    val profileInfoSharedFlow: SharedFlow<ResponseResultType<ProfileModel>>
        get() = profileInfoMutableSharedFlow.asSharedFlow()

    fun getProfileInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            profileInfoMutableSharedFlow.emit(getUserProfileUseCase())
        }
    }
}