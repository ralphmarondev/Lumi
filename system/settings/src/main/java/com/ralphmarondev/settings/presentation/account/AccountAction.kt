package com.ralphmarondev.settings.presentation.account

import android.net.Uri
import com.ralphmarondev.core.domain.model.Gender

sealed interface AccountAction {
    data object Refresh : AccountAction
    data object NavigateBack : AccountAction
    data object ResetNavigation : AccountAction

    data class ProfileImageChange(val path: Uri) : AccountAction

    data class SetDisplayNameDialogValue(val value: Boolean) : AccountAction
    data class UpdateDisplayName(val updatedDisplayName: String) : AccountAction

    data class SetUsernameDialogValue(val value: Boolean) : AccountAction
    data class UpdateUsername(val updatedUsername: String) : AccountAction

    data class SetEmailDialogValue(val value: Boolean) : AccountAction
    data class UpdateEmail(val updatedEmail: String) : AccountAction

    data class SetPhoneNumberDialogValue(val value: Boolean) : AccountAction
    data class UpdatePhoneNumber(val updatedPhoneNumber: String) : AccountAction

    data class SetGenderDialogValue(val value: Boolean) : AccountAction
    data class UpdateGender(val updatedGender: Gender) : AccountAction

    data class SetBirthdayDialogValue(val value: Boolean) : AccountAction
    data class UpdateBirthday(val updatedBirthday: String) : AccountAction
}