package com.lucky_lotto.mvi_test

import androidx.lifecycle.viewModelScope
import com.lucky_lotto.domain.repository.RemoteConfigRepository
import com.lucky_lotto.mvi_test.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class RequestUpdate(
    val isUpdate: Boolean = false,
    val fetchNote: String = ""
)

data class AdConfig(
    val probabilityPercent: Int = 30,
    val cooldownMinutes: Int = 10
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository
): BaseViewModel() {

    private val _requestUpdate = MutableStateFlow(RequestUpdate())
    val requestUpdate: StateFlow<RequestUpdate> = _requestUpdate.asStateFlow()

    private val _adConfig = MutableStateFlow(AdConfig())
    val adConfig: StateFlow<AdConfig> = _adConfig.asStateFlow()

    init {
        viewModelScope.launch {
            remoteConfigRepository.fetchAndActivate()
                .onSuccess { config ->
                    Timber.e("config : $config")
                    _requestUpdate.value = RequestUpdate(
                        isUpdate = config.isRequestUpdate,
                        fetchNote = config.fetchNote
                    )
                    _adConfig.value = AdConfig(
                        probabilityPercent = config.adShowProbability,
                        cooldownMinutes = config.adCooldownMinutes
                    )
                }
        }
    }
}
