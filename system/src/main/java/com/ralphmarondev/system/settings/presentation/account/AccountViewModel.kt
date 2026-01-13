package com.ralphmarondev.system.settings.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    init {
        refresh()
    }

    fun onAction(action: AccountAction) {
        when (action) {
            AccountAction.Refresh -> refresh()

            AccountAction.NavigateBack -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            AccountAction.ResetNavigation -> {
                _state.update {
                    it.copy(navigateBack = false)
                }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            delay(2000)
            _state.update {
                it.copy(
                    user = User(
                        displayName = "Ralph Maron A. Eda",
                        username = "ralphmaron@gmail.com"
                    )
                )
            }
        }
    }
}