package com.ralphmarondev.launcher.presentation

import com.ralphmarondev.core.domain.model.LumiApp

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
    object Phone : NavigationTarget()
    object Calculator : NavigationTarget()
    object Community : NavigationTarget()
}

data class LauncherState(
    val dockLumiApps: List<LumiApp> = emptyList(),
    val miniLumiApps: List<LumiApp> = emptyList(),
    val navigationTarget: NavigationTarget = NavigationTarget.None,
    val pageCount: Int = 2,
    val wallpaper: String = ""
)
