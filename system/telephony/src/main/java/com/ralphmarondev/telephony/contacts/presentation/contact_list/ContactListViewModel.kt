package com.ralphmarondev.telephony.contacts.presentation.contact_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.telephony.contacts.domain.repository.ContactsRepository
import kotlinx.coroutines.delay
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
                loadContacts(isUserRefreshing = true)
            }
        }
    }

    private fun loadContacts(isUserRefreshing: Boolean = false) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                        isRefreshing = isUserRefreshing,
                        errorMessage = null
                    )
                }
                val contacts = repository.getAllContact()
                delay(2000)
                _state.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        contacts = contacts
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = "Error: ${e.message}"
                    )
                }
            }
        }
    }
}