package com.ralphmarondev.settings.presentation.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountTree
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ContactPhone
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.ralphmarondev.core.domain.model.Gender
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import com.ralphmarondev.core.presentation.component.LumiTextField
import com.ralphmarondev.settings.R
import org.koin.compose.viewmodel.koinViewModel
import java.io.File
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth

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
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(state.showErrorMessage) {
        if (state.showErrorMessage) {
            snackbar.showSnackbar(
                message = state.errorMessage ?: "An error occurred."
            )
        }
    }

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
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbar)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = { action(AccountAction.Refresh) },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        UserImage(
                            imagePath = state.profileImagePath,
                            onImageSelected = { path ->
                                action(AccountAction.ProfileImageChange(path))
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
                                    value = state.displayName,
                                    onClick = { action(AccountAction.SetDisplayNameDialogValue(true)) }
                                )
                                HorizontalDivider(thickness = 0.3.dp)
                                AccountField(
                                    label = "Username",
                                    value = state.username,
                                    onClick = { action(AccountAction.SetUsernameDialogValue(true)) }
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
                                    value = state.email ?: "Not set",
                                    onClick = { action(AccountAction.SetEmailDialogValue(true)) }
                                )
                                HorizontalDivider(thickness = 0.3.dp)
                                AccountField(
                                    label = "Phone Number",
                                    value = state.phoneNumber ?: "Not set",
                                    onClick = { action(AccountAction.SetPhoneNumberDialogValue(true)) }
                                )
                                HorizontalDivider(thickness = 0.3.dp)
                                AccountField(
                                    label = "Gender",
                                    value = state.genderString,
                                    onClick = { action(AccountAction.SetGenderDialogValue(true)) }
                                )
                                HorizontalDivider(thickness = 0.3.dp)
                                AccountField(
                                    label = "Birthday",
                                    value = state.birthday ?: "Not set",
                                    onClick = { action(AccountAction.SetBirthdayDialogValue(true)) }
                                )
                            }
                        }
                    }
                }
            }

            if (state.showDisplayNameDialog) {
                DisplayNameDialog(
                    displayName = state.displayName,
                    onCancel = { action(AccountAction.SetDisplayNameDialogValue(false)) },
                    onUpdate = { updatedDisplayName ->
                        action(AccountAction.UpdateDisplayName(updatedDisplayName))
                    }
                )
            }

            if (state.showUsernameDialog) {
                UsernameDialog(
                    username = state.username,
                    onCancel = { action(AccountAction.SetUsernameDialogValue(false)) },
                    onUpdate = { updatedUsername ->
                        action(AccountAction.UpdateUsername(updatedUsername))
                    }
                )
            }

            if (state.showEmailDialog) {
                EmailDialog(
                    email = state.email ?: "",
                    onCancel = { action(AccountAction.SetEmailDialogValue(false)) },
                    onUpdate = { updatedEmail ->
                        action(AccountAction.UpdateEmail(updatedEmail))
                    }
                )
            }

            if (state.showPhoneNumberDialog) {
                PhoneNumberDialog(
                    phoneNumber = state.phoneNumber ?: "",
                    onCancel = { action(AccountAction.SetPhoneNumberDialogValue(false)) },
                    onUpdate = { updatedPhoneNumber ->
                        action(AccountAction.UpdatePhoneNumber(updatedPhoneNumber))
                    }
                )
            }

            if (state.showGenderDialog) {
                GenderDialog(
                    gender = state.gender,
                    onCancel = { action(AccountAction.SetGenderDialogValue(false)) },
                    onUpdate = { updatedGender ->
                        action(AccountAction.UpdateGender(updatedGender))
                    }
                )
            }

            if (state.showBirthdayDialog) {
                BirthdayDialog(
                    birthday = state.birthday ?: "",
                    onCancel = { action(AccountAction.SetBirthdayDialogValue(false)) },
                    onUpdate = { updatedBirthday ->
                        action(AccountAction.UpdateBirthday(updatedBirthday))
                    }
                )
            }
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

    val painter = rememberAsyncImagePainter(
        model = imagePath?.takeIf { it.isNotBlank() }?.let { File(it) },
        error = painterResource(R.drawable.ralphmaron),
        placeholder = painterResource(R.drawable.ralphmaron)
    )
    Image(
        painter = painter,
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
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIosNew,
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .rotate(180f),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
        }
    }
}

// Note: If we use Alert dialog or dialog, the status bar shows.
@Composable
private fun BoxScope.DisplayNameDialog(
    displayName: String,
    onCancel: () -> Unit,
    onUpdate: (String) -> Unit
) {
    var newDisplayName by rememberSaveable { mutableStateOf(displayName) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
    )
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .zIndex(3f)
            .padding(24.dp)
            .shadow(16.dp, shape = MaterialTheme.shapes.medium)
            .background(
                MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 240.dp)
                .padding(24.dp)
        ) {
            Text(
                text = "Display Name",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "This name will be displayed when you post comments on 'Community' and other apps.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            LumiTextField(
                value = newDisplayName,
                onValueChange = { newDisplayName = it },
                placeHolderText = "Lumi User",
                labelText = "Display Name",
                leadingIconImageVector = Icons.Outlined.ManageAccounts,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancel) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { onUpdate(newDisplayName) }) {
                    Text(text = "Update")
                }
            }
        }
    }
}

@Composable
private fun BoxScope.UsernameDialog(
    username: String,
    onCancel: () -> Unit,
    onUpdate: (String) -> Unit
) {
    var newUsername by rememberSaveable { mutableStateOf(username) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
    )
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .zIndex(3f)
            .padding(24.dp)
            .shadow(16.dp, shape = MaterialTheme.shapes.medium)
            .background(
                MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 240.dp)
                .padding(24.dp)
        ) {
            Text(
                text = "Username",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "This is your unique username. Others will use this to mention you or search for you in the system.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            LumiTextField(
                value = newUsername,
                onValueChange = { newUsername = it },
                placeHolderText = "ralphmaron",
                labelText = "Username",
                leadingIconImageVector = Icons.Outlined.AccountTree,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancel) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { onUpdate(newUsername) }) {
                    Text(text = "Update")
                }
            }
        }
    }
}

@Composable
private fun BoxScope.EmailDialog(
    email: String,
    onCancel: () -> Unit,
    onUpdate: (String) -> Unit
) {
    var newEmail by rememberSaveable { mutableStateOf(email) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
    )
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .zIndex(3f)
            .padding(24.dp)
            .shadow(16.dp, shape = MaterialTheme.shapes.medium)
            .background(
                MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 240.dp)
                .padding(24.dp)
        ) {
            Text(
                text = "Set Email",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your email is used for account recovery, notifications, and signing in.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            LumiTextField(
                value = newEmail,
                onValueChange = { newEmail = it },
                placeHolderText = "user@lumi.org",
                labelText = "Email",
                leadingIconImageVector = Icons.Outlined.Email,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onCancel) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { onUpdate(newEmail) }) {
                    Text(text = "Update")
                }
            }
        }
    }
}

@Composable
private fun BoxScope.PhoneNumberDialog(
    phoneNumber: String,
    onCancel: () -> Unit,
    onUpdate: (String) -> Unit
) {
    var newPhoneNumber by rememberSaveable { mutableStateOf(phoneNumber) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
    )
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .zIndex(3f)
            .padding(24.dp)
            .shadow(16.dp, shape = MaterialTheme.shapes.medium)
            .background(
                MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 240.dp)
                .padding(24.dp)
        ) {
            Text(
                text = "Set Phone Number",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Add your phone number to help secure your account and enable two-factor authentication.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            LumiTextField(
                value = newPhoneNumber,
                onValueChange = { newPhoneNumber = it },
                placeHolderText = "9617******",
                labelText = "Phone Number",
                leadingIconImageVector = Icons.Outlined.ContactPhone,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                prefix = {
                    Text(
                        text = "+63",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onCancel) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { onUpdate(newPhoneNumber) }) {
                    Text(text = "Update")
                }
            }
        }
    }
}

@Composable
private fun BoxScope.GenderDialog(
    gender: Gender,
    onCancel: () -> Unit,
    onUpdate: (Gender) -> Unit
) {
    val currentGender = when (gender) {
        Gender.NotSet -> Gender.Male
        Gender.Male -> Gender.Male
        Gender.Female -> Gender.Female
    }
    var selectedGender by rememberSaveable { mutableStateOf(currentGender) }
    val genderOptions = listOf(Gender.Male, Gender.Female)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
    )
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .zIndex(3f)
            .padding(24.dp)
            .shadow(16.dp, shape = MaterialTheme.shapes.medium)
            .background(
                MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 240.dp)
                .padding(24.dp)
        ) {
            Text(
                text = "Set Gender",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Select your gender. This information can be used for personalization across apps.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            genderOptions.forEach { option ->
                OutlinedCard(
                    onClick = { selectedGender = option },
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedGender == option,
                            onClick = {},
                            enabled = false,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.secondary,
                                disabledSelectedColor = MaterialTheme.colorScheme.primary,
                                disabledUnselectedColor = MaterialTheme.colorScheme.secondary
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = option.name,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onCancel) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { onUpdate(selectedGender) }) {
                    Text(text = "Update")
                }
            }
        }
    }
}

@Composable
private fun BoxScope.BirthdayDialog(
    birthday: String,
    onCancel: () -> Unit,
    onUpdate: (String) -> Unit
) {
    var newBirthday by rememberSaveable { mutableStateOf(birthday) }

    val months =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val currentYear = Year.now().value
    val years = (currentYear downTo currentYear - 100).toList()

    val initialDate = runCatching { LocalDate.parse(birthday) }.getOrNull()
    var selectedMonth by rememberSaveable {
        mutableIntStateOf(
            initialDate?.monthValue?.minus(1) ?: 0
        )
    }
    var selectedYear by rememberSaveable { mutableIntStateOf(initialDate?.year ?: currentYear) }

    val daysInMonth = remember(selectedMonth, selectedYear) {
        YearMonth.of(selectedYear, selectedMonth + 1).lengthOfMonth()
    }
    var selectedDay by rememberSaveable {
        mutableIntStateOf(
            initialDate?.dayOfMonth?.coerceAtMost(
                daysInMonth
            ) ?: 1
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
    )
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .zIndex(3f)
            .padding(24.dp)
            .shadow(16.dp, shape = MaterialTheme.shapes.medium)
            .background(
                MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 240.dp)
                .padding(24.dp)
        ) {
            Text(
                text = "Select Birthday",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your birthday helps us personalize your experience and is used for age verification.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                val days = (1..daysInMonth).toList()

                WheelPicker(
                    items = months,
                    selectedItem = months[selectedMonth],
                    onItemSelected = { selectedMonth = months.indexOf(it) },
                    modifier = Modifier.weight(1f)
                ) { month, isSelected ->
                    Text(
                        text = month,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                WheelPicker(
                    items = days,
                    selectedItem = selectedDay,
                    onItemSelected = { selectedDay = it },
                    modifier = Modifier.weight(1f)
                ) { day, isSelected ->
                    Text(
                        text = day.toString(),
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                WheelPicker(
                    items = years,
                    selectedItem = selectedYear,
                    onItemSelected = { selectedYear = it },
                    modifier = Modifier.weight(1f)
                ) { year, isSelected ->
                    Text(
                        text = year.toString(),
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancel) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { onUpdate(newBirthday) }) {
                    Text(text = "Update")
                }
            }
        }
    }
}

@Composable
private fun <T> WheelPicker(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    rowHeight: Dp = 50.dp,
    content: @Composable (item: T, isSelected: Boolean) -> Unit
) {
    Box(modifier = modifier.height(rowHeight * 3)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = rowHeight)
        ) {
            items(items) { item ->
                Box(
                    modifier = Modifier
                        .height(rowHeight)
                        .fillMaxWidth()
                        .clickable { onItemSelected(item) },
                    contentAlignment = Alignment.Center
                ) {
                    val isSelected = item == selectedItem
                    content(item, isSelected)
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .height(rowHeight)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
        )
    }
}