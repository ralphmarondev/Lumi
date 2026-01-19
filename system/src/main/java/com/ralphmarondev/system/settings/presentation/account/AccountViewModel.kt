package com.ralphmarondev.system.settings.presentation.account

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.domain.model.Gender
import com.ralphmarondev.core.domain.model.Result
import com.ralphmarondev.system.settings.domain.repository.SettingsRepository
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

            is AccountAction.ShowEditDialog -> _state.update { it.copy(editField = action.field) }
            AccountAction.DismissEditDialog -> _state.update { it.copy(editField = null) }
            is AccountAction.UpdateField -> _state.update {
                val updatedUser = when (action.field) {
                    EditField.DISPLAY_NAME -> it.user.copy(displayName = action.value)
                    EditField.USERNAME -> it.user.copy(username = action.value)
                    EditField.EMAIL -> it.user.copy(email = action.value)
                    EditField.PHONE -> it.user.copy(phoneNumber = action.value)
                    EditField.GENDER -> {
                        it.user.copy(
                            gender = when (action.value) {
                                "Male" -> Gender.Male
                                "Female" -> Gender.Female
                                else -> Gender.NotSet
                            }
                        )
                    }

                    EditField.BIRTHDAY -> it.user.copy(birthday = action.value)
                }
                it.copy(user = updatedUser, editField = null)
            }

            is AccountAction.SelectImage -> {
                viewModelScope.launch {
                    val imagePath = saveImageToInternalStorage(action.path)

                    if (imagePath != null) {
                        _state.update {
                            it.copy(
                                user = _state.value.user.copy(
                                    profileImagePath = imagePath
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadUserInformation() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = repository.getUserInformation()

            when (result) {
                is Result.Success -> {
                    _state.update {
                        it.copy(user = it.user, isLoading = false)
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
                val file = File(context.filesDir, "profile.png")

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