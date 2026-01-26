package com.ralphmarondev.launcher.presentation

import com.ralphmarondev.launcher.domain.model.MiniApp

sealed class NavigationTarget {
    object None : NavigationTarget()
    object Settings : NavigationTarget()
    object Notes : NavigationTarget()
    object Clock : NavigationTarget()
    object Weather : NavigationTarget()
    object Calendar : NavigationTarget()
    object Camera : NavigationTarget()
    object Photos : NavigationTarget()
    object Videos : NavigationTarget()
    object Contacts : NavigationTarget()
}

data class LauncherState(
    val dockApps: List<MiniApp> = emptyList(),
    val miniApps: List<MiniApp> = emptyList(),
    val navigationTarget: NavigationTarget = NavigationTarget.None,
    val pageCount: Int = 2,
    val wallpaper: String = ""
)
