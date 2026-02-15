package com.ralphmarondev.boot.setup.presentation.install

sealed interface InstallAction {
    data object Retry : InstallAction
    data object Continue : InstallAction
    data object Previous : InstallAction
}