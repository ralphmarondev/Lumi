package com.ralphmarondev.telephony.contacts

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.telephony.contacts.presentation.contact_list.ContactListScreenRoot
import com.ralphmarondev.telephony.contacts.presentation.new_contact.NewContactScreenRoot

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
            NewContactScreenRoot(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}