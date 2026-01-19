package com.ralphmarondev.system.settings.presentation.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import com.ralphmarondev.system.R
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AccountScreenRoot(
    navigateBack: () -> Unit
) {
    val viewModel: AccountViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            navigateBack()
            viewModel.onAction(AccountAction.ResetNavigation)
        }
    }

    LumiGestureHandler(onBackSwipe = navigateBack) {
        AccountScreen(
            state = state,
            action = viewModel::onAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountScreen(
    state: AccountState,
    action: (AccountAction) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Personal Information")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { action(AccountAction.NavigateBack) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { action(AccountAction.Refresh) },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    UserImage(
                        imagePath = state.user.profileImagePath,
                        onImageSelected = {
                            action(AccountAction.SelectImage(it))
                        },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                    )
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        )
                    ) {
                        Column {
                            AccountField(
                                label = "Display Name",
                                value = state.user.displayName,
                                onClick = {
                                    action(AccountAction.ShowEditDialog(EditField.DISPLAY_NAME))
                                }
                            )
                            HorizontalDivider(thickness = 0.3.dp)
                            AccountField(
                                label = "Username",
                                value = state.user.username,
                                onClick = {
                                    action(AccountAction.ShowEditDialog(EditField.USERNAME))
                                }
                            )
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        )
                    ) {
                        Column {
                            AccountField(
                                label = "Email",
                                value = state.user.email ?: "NOT_SET",
                                onClick = {
                                    action(AccountAction.ShowEditDialog(EditField.EMAIL))
                                }
                            )
                            HorizontalDivider(thickness = 0.3.dp)
                            AccountField(
                                label = "Phone Number",
                                value = state.user.phoneNumber ?: "NOT_SET",
                                onClick = {
                                    action(AccountAction.ShowEditDialog(EditField.PHONE))
                                }
                            )
                            HorizontalDivider(thickness = 0.3.dp)
                            AccountField(
                                label = "Gender",
                                value = state.user.gender.toString(),
                                onClick = {
                                    action(AccountAction.ShowEditDialog(EditField.GENDER))
                                }
                            )
                            HorizontalDivider(thickness = 0.3.dp)
                            AccountField(
                                label = "Birthday",
                                value = state.user.birthday,
                                onClick = {
                                    action(AccountAction.ShowEditDialog(EditField.BIRTHDAY))
                                }
                            )
                        }
                    }
                }
            }
        }

        state.editField?.let { field ->
            val (title, desc, value) = when (field) {
                EditField.DISPLAY_NAME -> Triple(
                    "Display Name",
                    "Change your display name",
                    state.user.displayName
                )

                EditField.USERNAME -> Triple(
                    "Username",
                    "Change your username",
                    state.user.username
                )

                EditField.EMAIL -> Triple("Email", "Update your email address", state.user.email)
                EditField.PHONE -> Triple(
                    "Phone Number",
                    "Update your phone number",
                    state.user.phoneNumber
                )

                EditField.GENDER -> Triple(
                    "Gender",
                    "Update your gender",
                    state.user.gender.name
                )

                EditField.BIRTHDAY -> Triple(
                    "Birthday",
                    "Update your birthday",
                    state.user.birthday
                )
            }
            EditFieldDialog(
                title = title,
                description = desc,
                value = value,
                onDismiss = { action(AccountAction.DismissEditDialog) },
                onConfirm = { newValue ->
                    action(AccountAction.UpdateField(field, newValue))
                }
            )
        }
    }
}

@Composable
private fun UserImage(
    imagePath: String?,
    onImageSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onImageSelected(it) }
    }

    Image(
        painter = rememberAsyncImagePainter(imagePath ?: R.drawable.ralphmaron),
        contentDescription = "User Image",
        modifier = modifier
            .size(140.dp)
            .clip(CircleShape)
            .clickable {
                launcher.launch("image/*")
            },
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun AccountField(label: String, value: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIosNew,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .size(18.dp)
                        .rotate(180f),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
private fun EditFieldDialog(
    title: String,
    description: String,
    value: String?,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf(value ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Column {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(text) }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}