package com.ralphmarondev.community.auth.presentation.login

sealed interface LoginAction {
    data object Login : LoginAction
    data object Register : LoginAction
    data object NavigateBack : LoginAction
    data object ClearNavigation : LoginAction
}