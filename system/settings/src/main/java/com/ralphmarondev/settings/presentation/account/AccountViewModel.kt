package com.ralphmarondev.settings.presentation.account

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.domain.model.Gender
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class AccountViewModel(
    application: Application,
    private val repository: SettingsRepository
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    init {
        loadUserInformation()
    }

    fun onAction(action: AccountAction) {
        when (action) {
            AccountAction.Refresh -> {
                loadUserInformation(isRefreshing = true)
            }

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

            is AccountAction.ProfileImageChange -> {}

            // DISPLAY NAME
            is AccountAction.SetDisplayNameDialogValue -> {
                if (
                    _state.value.showUsernameDialog ||
                    _state.value.showEmailDialog ||
                    _state.value.showPhoneNumberDialog ||
                    _state.value.showGenderDialog ||
                    _state.value.showBirthdayDialog
                ) {
                    return
                }
                _state.update { it.copy(showDisplayNameDialog = action.value) }
            }

            is AccountAction.UpdateDisplayName -> {
                updateDisplayName(action.updatedDisplayName.trim())
            }

            // EMAIL
            is AccountAction.SetEmailDialogValue -> {
                if (
                    _state.value.showUsernameDialog ||
                    _state.value.showDisplayNameDialog ||
                    _state.value.showPhoneNumberDialog ||
                    _state.value.showGenderDialog ||
                    _state.value.showBirthdayDialog
                ) {
                    return
                }
                _state.update { it.copy(showEmailDialog = action.value) }
            }

            is AccountAction.UpdateEmail -> {
                updateEmail(updatedEmail = action.updatedEmail.trim())
            }

            // PHONE NUMBER
            is AccountAction.SetPhoneNumberDialogValue -> {
                if (
                    _state.value.showUsernameDialog ||
                    _state.value.showEmailDialog ||
                    _state.value.showDisplayNameDialog ||
                    _state.value.showGenderDialog ||
                    _state.value.showBirthdayDialog
                ) {
                    return
                }
                _state.update { it.copy(showPhoneNumberDialog = action.value) }
            }

            is AccountAction.UpdatePhoneNumber -> {
                updatePhoneNumber(action.updatedPhoneNumber.trim())
            }

            // GENDER
            is AccountAction.SetGenderDialogValue -> {
                if (
                    _state.value.showUsernameDialog ||
                    _state.value.showEmailDialog ||
                    _state.value.showPhoneNumberDialog ||
                    _state.value.showDisplayNameDialog ||
                    _state.value.showBirthdayDialog
                ) {
                    return
                }
                _state.update { it.copy(showGenderDialog = action.value) }
            }

            is AccountAction.UpdateGender -> {
                updateGender(action.updatedGender)
            }

            // BIRTHDAY
            is AccountAction.SetBirthdayDialogValue -> {
                if (
                    _state.value.showUsernameDialog ||
                    _state.value.showEmailDialog ||
                    _state.value.showPhoneNumberDialog ||
                    _state.value.showGenderDialog ||
                    _state.value.showDisplayNameDialog
                ) {
                    return
                }
                _state.update { it.copy(showBirthdayDialog = action.value) }
            }

            is AccountAction.UpdateBirthday -> {
                updateBirthday(action.updatedBirthday.trim())
            }

            // USERNAME
            is AccountAction.SetUsernameDialogValue -> {
                if (
                    _state.value.showDisplayNameDialog ||
                    _state.value.showEmailDialog ||
                    _state.value.showPhoneNumberDialog ||
                    _state.value.showGenderDialog ||
                    _state.value.showBirthdayDialog
                ) {
                    return
                }
                _state.update { it.copy(showUsernameDialog = action.value) }
            }

            is AccountAction.UpdateUsername -> {
                updateUsername(action.updatedUsername.trim())
            }
        }
    }

    private fun loadUserInformation(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                        isRefreshing = isRefreshing,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }

                when (val result = repository.getUserInformation()) {
                    is Result.Success -> {
                        val genderString = when (result.data.gender) {
                            Gender.NotSet -> "Not set"
                            Gender.Male -> "Male"
                            Gender.Female -> "Female"
                        }
                        _state.update {
                            it.copy(
                                displayName = result.data.displayName,
                                username = result.data.username,
                                email = result.data.email,
                                phoneNumber = result.data.phoneNumber,
                                gender = result.data.gender,
                                birthday = result.data.birthday,
                                profileImagePath = result.data.profileImagePath,
                                genderString = genderString
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(errorMessage = it.errorMessage)
                        }
                    }

                    Result.Loading -> Unit
                }
                _state.update { it.copy(isRefreshing = false) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Error: ${e.message}",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun saveImageToInternalStorage(imageUri: Uri): String? {
        return try {
            withContext(Dispatchers.IO) {
                val context = getApplication<Application>()
                val file = File(context.filesDir, "profile_${System.currentTimeMillis()}.png")

                context.contentResolver.openInputStream(imageUri)?.use { input ->
                    FileOutputStream(file, false).use { output ->
                        input.copyTo(output)
                    }
                }
                file.absolutePath
            }
        } catch (e: Exception) {
            Log.e("Account", "Error saving image to internal storage.", e)
            null
        }
    }

    private fun updateDisplayName(updatedDisplayName: String) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }

                if (updatedDisplayName.isBlank()) {
                    _state.update {
                        it.copy(
                            errorMessage = "Display name cannot be updated with blank value.",
                            showErrorMessage = true
                        )
                    }
                    return@launch
                }

                repository.updateDisplayName(displayName = updatedDisplayName)
                _state.update {
                    it.copy(
                        showDisplayNameDialog = false,
                        displayName = updatedDisplayName
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Failed updating display name. Error: ${e.message}",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun updateUsername(updatedUsername: String) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }

                if (updatedUsername.isBlank()) {
                    _state.update {
                        it.copy(
                            errorMessage = "Username cannot be updated with blank value.",
                            showErrorMessage = true
                        )
                    }
                    return@launch
                }

                repository.updateUsername(updatedUsername)
                _state.update {
                    it.copy(
                        showUsernameDialog = false,
                        username = updatedUsername
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Failed updating username. Error: ${e.message}",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun updateEmail(updatedEmail: String) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }

                if (updatedEmail.isBlank()) {
                    _state.update {
                        it.copy(
                            errorMessage = "Email cannot be updated with blank value.",
                            showErrorMessage = true
                        )
                    }
                    return@launch
                }

                repository.updateEmail(updatedEmail)
                _state.update {
                    it.copy(
                        showEmailDialog = false,
                        email = updatedEmail
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Failed updating email. Error: ${e.message}",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun updatePhoneNumber(updatedPhoneNumber: String) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }

                if (updatedPhoneNumber.isBlank()) {
                    _state.update {
                        it.copy(
                            errorMessage = "Phone number cannot be updated with blank value.",
                            showErrorMessage = true
                        )
                    }
                    return@launch
                }

                repository.updatePhoneNumber(updatedPhoneNumber)
                _state.update {
                    it.copy(
                        showPhoneNumberDialog = false,
                        phoneNumber = updatedPhoneNumber
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Failed updating display name. Error: ${e.message}",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun updateGender(updatedGender: Gender) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }

                repository.updateGender(updatedGender)
                _state.update {
                    it.copy(
                        showGenderDialog = false,
                        gender = updatedGender
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Failed updating gender. Error: ${e.message}",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun updateBirthday(updatedBirthday: String) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }

                if (updatedBirthday.isBlank()) {
                    _state.update {
                        it.copy(
                            errorMessage = "Birthday cannot be updated with blank value.",
                            showErrorMessage = true
                        )
                    }
                    return@launch
                }

                repository.updateBirthday(updatedBirthday)
                _state.update {
                    it.copy(
                        showBirthdayDialog = false,
                        birthday = updatedBirthday
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Failed updating birthday. Error: ${e.message}",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}