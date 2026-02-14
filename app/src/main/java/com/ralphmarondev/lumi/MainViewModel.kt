package com.ralphmarondev.lumi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.data.local.preferences.LumiPreferences
import com.ralphmarondev.lumi.navigation.LumiApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(
    private val preferences: LumiPreferences
) : ViewModel() {

    private val _startApp = MutableStateFlow<LumiApp?>(null)
    val startApp = _startApp.asStateFlow()

    init {
        viewModelScope.launch {
            val setupCompleted = preferences.isSystemOnboardingCompleted().first()
            val authenticated = preferences.isAuthenticated().first()
            val enabledAuth = preferences.isSystemEnableAuth().first()
            delay(4000)

            _startApp.value = when {
                setupCompleted && authenticated && !enabledAuth -> LumiApp.Launcher
                setupCompleted && authenticated && enabledAuth -> LumiApp.Login
                setupCompleted -> LumiApp.Login
                else -> LumiApp.Setup
            }
        }
    }
}