package com.ralphmarondev.settings.presentation.account

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.core.domain.model.User
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

    private val _initialUserData = MutableStateFlow(User())

    init {
        viewModelScope.launch {
            loadUserInformation()
            _initialUserData.update {
                it.copy(
                    displayName = _state.value.displayName,
                    username = _state.value.username,
                    email = _state.value.email,
                    profileImagePath = _state.value.profileImagePath
                )
            }
        }
    }

    fun onAction(action: AccountAction) {
        when (action) {
            AccountAction.Refresh -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(isRefreshing = true)
                    }
                    loadUserInformation()
                    _state.update {
                        it.copy(isRefreshing = false)
                    }
                }
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

            is AccountAction.ProfileImageChange -> {
                viewModelScope.launch {
                    val imagePath = saveImageToInternalStorage(action.path)

                    if (imagePath != null) {
                        val updatedUser = _initialUserData.value.copy(
                            profileImagePath = imagePath
                        )

                        when (repository.updateUserInformation(updatedUser)) {
                            is Result.Success -> {
                                _state.update {
                                    it.copy(profileImagePath = imagePath)
                                }
                            }

                            else -> Unit
                        }
                    }
                }
            }

            is AccountAction.DisplayNameChange -> {
                Log.d("Account", "DisplayName: ${action.displayName}")
                _state.update {
                    it.copy(displayName = action.displayName)
                }
            }

            is AccountAction.UsernameChange -> {}
            is AccountAction.EmailChange -> {}
            is AccountAction.PhoneNumberChange -> {}
            is AccountAction.GenderChange -> {}
            is AccountAction.BirthdayChange -> {}

            AccountAction.ToggleBirthdayDialog -> {}
            AccountAction.ToggleDisplayNameDialog -> {
                _state.update {
                    it.copy(showDisplayNameDialog = !_state.value.showDisplayNameDialog)
                }
            }

            AccountAction.ToggleEmailDialog -> {}
            AccountAction.ToggleGenderDialog -> {}
            AccountAction.TogglePhoneNumberDialog -> {}
            AccountAction.ToggleUsernameDialog -> {}
            AccountAction.UpdateDisplayName -> {
                viewModelScope.launch {
                    Log.d("Account", "Updating display name...")
                    if (_state.value.displayName.trim() == _initialUserData.value.displayName) {
                        Log.d("Account", "Display name did not changed.")
                        return@launch
                    }
                    if (_state.value.displayName.isBlank()) {
                        Log.d("Account", "Display name is blank.")
                        _state.update {
                            it.copy(errorMessage = "Display name is empty.")
                        }
                        return@launch
                    }

                    repository.updateDisplayName(displayName = _state.value.displayName.trim())
                    Log.d("Account", "Done...")
                    _state.update {
                        it.copy(showDisplayNameDialog = false)
                    }
                }
            }
        }
    }

    private suspend fun loadUserInformation() {
        _state.update { it.copy(isLoading = true) }
        val result = repository.getUserInformation()

        when (result) {
            is Result.Success -> {
                _state.update {
                    it.copy(
                        displayName = it.displayName,
                        username = it.username,
                        email = it.email,
                        phoneNumber = it.phoneNumber,
                        gender = it.gender,
                        birthday = it.birthday,
                        profileImagePath = it.profileImagePath
                    )
                }
            }

            is Result.Error -> {
                _state.update {
                    it.copy(errorMessage = it.errorMessage, isLoading = false)
                }
            }

            Result.Loading -> Unit
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
}