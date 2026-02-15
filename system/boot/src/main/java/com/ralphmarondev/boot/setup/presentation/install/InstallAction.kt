package com.ralphmarondev.boot.setup.presentation.install

sealed interface InstallAction {
    data object Retry : InstallAction
}