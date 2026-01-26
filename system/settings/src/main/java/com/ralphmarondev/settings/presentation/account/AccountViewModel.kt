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

    private val _currentUser = MutableStateFlow(User())

    init {
        loadUserInformation()
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
                        val updatedUser = _currentUser.value.copy(
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
                viewModelScope.launch {
                    val user = _currentUser.value.copy(displayName = action.displayName)
                    val result = repository.updateUserInformation(user)

                    when (result) {
                        is Result.Success -> {
                            _currentUser.update { it.copy(displayName = action.displayName) }
                            _state.update {
                                it.copy(showDisplayNameDialog = !_state.value.showDisplayNameDialog)
                            }
                        }

                        is Result.Error -> Unit
                        Result.Loading -> Unit
                    }
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
        }
    }

    private fun loadUserInformation() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = repository.getUserInformation()

            when (result) {
                is Result.Success -> {
                    _currentUser.update {
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
                    _state.update {
                        val user = _currentUser.value
                        it.copy(
                            displayName = user.displayName,
                            username = user.username,
                            email = user.email ?: "NOT_SET",
                            phoneNumber = user.phoneNumber ?: "NOT_SET",
                            gender = user.gender.name, // UPDATE THIS LATER
                            birthday = user.birthday,
                            profileImagePath = user.profileImagePath ?: ""
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