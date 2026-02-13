package com.ralphmarondev.telephony.contacts.presentation.new_contact

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ContactPhone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ralphmarondev.core.presentation.component.LumiButton
import com.ralphmarondev.core.presentation.component.LumiTextField
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewContactScreenRoot(
    navigateBack: () -> Unit
) {
    val viewModel: NewContactViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            navigateBack()
            viewModel.onAction(NewContactAction.ResetNavigation)
        }
    }

    NewContactScreen(
        state = state,
        action = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewContactScreen(
    state: NewContactState,
    action: (NewContactAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "New Contact")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            action(NewContactAction.Back)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                LumiTextField(
                    value = state.firstName,
                    onValueChange = { action(NewContactAction.FirstNameChange(it)) },
                    placeHolderText = "Enter first name",
                    labelText = "First Name",
                    leadingIconImageVector = Icons.Outlined.PersonOutline,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )
                LumiTextField(
                    value = state.lastName,
                    onValueChange = { action(NewContactAction.LastNameChange(it)) },
                    placeHolderText = "Enter last name",
                    labelText = "Last Name",
                    leadingIconImageVector = Icons.Outlined.PersonOutline,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )
                LumiTextField(
                    value = state.phoneNumber,
                    onValueChange = { action(NewContactAction.PhoneNumberChange(it)) },
                    placeHolderText = "Enter phone number",
                    labelText = "Phone Number",
                    leadingIconImageVector = Icons.Outlined.ContactPhone,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))
                LumiButton(
                    text = if (state.isLoading) {
                        "Saving..."
                    } else {
                        "Save Contact"
                    },
                    onClick = {
                        focusManager.clearFocus()
                        action(NewContactAction.Save)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = !state.isLoading
                )
            }
        }
    }
}