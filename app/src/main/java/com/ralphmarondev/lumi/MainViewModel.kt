package com.ralphmarondev.lumi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.lumi.navigation.SystemApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _startApp = MutableStateFlow<SystemApp?>(null)
    val startApp = _startApp.asStateFlow()

    init {
        viewModelScope.launch {
            val setupCompleted = preferences.isSystemOnboardingCompleted().first()
            val authenticated = preferences.isAuthenticated().first()
            val enabledAuth = preferences.isSystemEnableAuth().first()
            delay(4000)

            _startApp.value = when {
                setupCompleted && authenticated && !enabledAuth -> SystemApp.Launcher
                setupCompleted && authenticated && enabledAuth -> SystemApp.Login
                setupCompleted -> SystemApp.Login
                else -> SystemApp.Setup
            }
        }
    }
}