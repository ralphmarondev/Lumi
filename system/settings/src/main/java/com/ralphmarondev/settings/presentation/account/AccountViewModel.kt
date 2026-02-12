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
                _state.update { it.copy(showDisplayNameDialog = action.value) }
            }

            is AccountAction.UpdateDisplayName -> {
                updateDisplayName(action.updatedDisplayName.trim())
            }

            // EMAIL
            is AccountAction.SetEmailDialogValue -> {
                _state.update { it.copy(showEmailDialog = action.value) }
            }

            is AccountAction.UpdateEmail -> {
                updateEmail(updatedEmail = action.updatedEmail.trim())
            }

            // PHONE NUMBER
            is AccountAction.SetPhoneNumberDialogValue -> {
                _state.update { it.copy(showPhoneNumberDialog = action.value) }
            }

            is AccountAction.UpdatePhoneNumber -> {
                updatePhoneNumber(action.updatedPhoneNumber.trim())
            }

            // GENDER
            is AccountAction.SetGenderDialogValue -> {
                _state.update { it.copy(showGenderDialog = action.value) }
            }

            is AccountAction.UpdateGender -> {
                updateGender(action.updatedGender)
            }

            // BIRTHDAY
            is AccountAction.SetBirthdayDialogValue -> {
                _state.update { it.copy(showBirthdayDialog = action.value) }
            }

            is AccountAction.UpdateBirthday -> {}

            // USERNAME
            is AccountAction.SetUsernameDialogValue -> {
                _state.update { it.copy(showUsernameDialog = action.value) }
            }

            is AccountAction.UpdateUsername -> {}
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
                    it.copy(showDisplayNameDialog = false)
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

    private fun updateEmail(updatedEmail: String) {

    }

    private fun updatePhoneNumber(updatedPhoneNumber: String) {

    }

    private fun updateGender(updatedGender: Gender) {

    }
}