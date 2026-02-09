package com.ralphmarondev.telephony.contacts

import androidx.compose.runtime.Composable
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
                navigateBack = finishActivity
            )
        }
    }
}