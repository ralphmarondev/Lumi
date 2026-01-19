package com.ralphmarondev.system.settings.presentation.account

import android.net.Uri

sealed interface AccountAction {
    data object Refresh : AccountAction
    data object NavigateBack : AccountAction
    data object ResetNavigation : AccountAction
    data class SelectImage(val path: Uri) : AccountAction
    data class ShowEditDialog(val field: EditField) : AccountAction
    object DismissEditDialog : AccountAction
    data class UpdateField(val field: EditField, val value: String) : AccountAction
}