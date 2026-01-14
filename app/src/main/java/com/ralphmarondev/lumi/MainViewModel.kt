package com.ralphmarondev.lumi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.data.local.preferences.AppPreferences
import com.ralphmarondev.lumi.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _startDestination = MutableStateFlow<Routes?>(null)
    val startDestination = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            val setupCompleted = preferences.isSystemOnboardingCompleted().first()
            val authenticated = preferences.isAuthenticated().first()
            val enabledAuth = preferences.isSystemEnableAuth().first()

            _startDestination.value = when {
                setupCompleted && authenticated && !enabledAuth -> Routes.Launcher
                setupCompleted && authenticated && enabledAuth -> Routes.Login
                setupCompleted -> Routes.Login
                else -> Routes.Setup
            }
        }
    }
}