package com.ralphmarondev.telephony.contacts.presentation.new_contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.core.domain.model.Contact
import com.ralphmarondev.telephony.contacts.domain.repository.ContactsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewContactViewModel(
    private val repository: ContactsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NewContactState())
    val state = _state.asStateFlow()

    fun onAction(action: NewContactAction) {
        when (action) {
            NewContactAction.Back -> {
                _state.update { it.copy(navigateBack = true) }
            }

            NewContactAction.Save -> {
                saveNewContact()
            }

            NewContactAction.ResetNavigation -> {
                _state.update { it.copy(navigateBack = false) }
            }

            is NewContactAction.FirstNameChange -> {
                _state.update {
                    it.copy(firstName = action.firstName)
                }
            }

            is NewContactAction.LastNameChange -> {
                _state.update {
                    it.copy(lastName = action.lastName)
                }
            }

            is NewContactAction.PhoneNumberChange -> {
                _state.update {
                    it.copy(phoneNumber = action.phoneNumber)
                }
            }
        }
    }

    private fun saveNewContact() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(isLoading = true, errorMessage = null)
                }
                val newContact = Contact(
                    firstName = _state.value.firstName.trim(),
                    lastName = _state.value.lastName.trim(),
                    phoneNumber = _state.value.phoneNumber.trim()
                )
                repository.create(newContact)
                delay(2000)
                _state.update {
                    it.copy(
                        isLoading = false,
                        firstName = "",
                        lastName = "",
                        phoneNumber = "",
                        contactImagePath = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(errorMessage = "Error: ${e.message}", isLoading = false)
                }
            }
        }
    }
}