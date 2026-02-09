package com.ralphmarondev.telephony.contacts.presentation.contact_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.telephony.contacts.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val repository: ContactsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ContactListState())
    val state = _state.asStateFlow()

    init {
        loadContacts()
    }

    fun onAction(action: ContactListAction) {
        when (action) {
            ContactListAction.Back -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            ContactListAction.Create -> {
                _state.update {
                    it.copy(createNewContact = true)
                }
            }

            ContactListAction.ResetNavigation -> {
                _state.update {
                    it.copy(
                        navigateBack = false,
                        createNewContact = false
                    )
                }
            }

            ContactListAction.Refresh -> {
                _state.update {
                    it.copy(isRefreshing = true)
                }
                loadContacts()
                _state.update {
                    it.copy(isRefreshing = false)
                }
            }
        }
    }

    private fun loadContacts() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(isLoading = true, errorMessage = null)
                }
                val contacts = repository.getAllContact()
                _state.update {
                    it.copy(
                        isLoading = false,
                        contacts = contacts
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.message}"
                    )
                }
            }
        }
    }
}