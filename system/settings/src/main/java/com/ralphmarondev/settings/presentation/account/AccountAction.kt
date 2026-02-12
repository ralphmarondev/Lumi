package com.ralphmarondev.settings.presentation.account

import android.net.Uri
import com.ralphmarondev.core.domain.model.Gender

sealed interface AccountAction {
    data object Refresh : AccountAction
    data object NavigateBack : AccountAction
    data object ResetNavigation : AccountAction

    data class ProfileImageChange(val path: Uri) : AccountAction

    data class SetDisplayNameDialogValue(val value: Boolean) : AccountAction
    data class DisplayNameChange(val displayName: String) : AccountAction
    data object UpdateDisplayName : AccountAction

    data object ToggleUsernameDialog : AccountAction
    data class UsernameChange(val username: String) : AccountAction

    data object ToggleEmailDialog : AccountAction
    data class EmailChange(val email: String) : AccountAction

    data object TogglePhoneNumberDialog : AccountAction
    data class PhoneNumberChange(val phoneNumber: String) : AccountAction

    data object ToggleGenderDialog : AccountAction
    data class GenderChange(val gender: Gender) : AccountAction

    data object ToggleBirthdayDialog : AccountAction
    data class BirthdayChange(val birthday: String) : AccountAction
}