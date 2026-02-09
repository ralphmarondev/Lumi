package com.ralphmarondev.telephony.contacts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.telephony.contacts.presentation.contact_list.ContactListScreenRoot

@Composable
fun ContactsNavigation(
    finishActivity: () -> Unit,
    startDestination: ContactsRoute = ContactsRoute.ContactList,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<ContactsRoute.ContactList> {
            ContactListScreenRoot(
                navigateBack = finishActivity,
                createNewContact = {
                    navController.navigate(ContactsRoute.NewContact) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<ContactsRoute.NewContact> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "New Contact",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }
    }
}