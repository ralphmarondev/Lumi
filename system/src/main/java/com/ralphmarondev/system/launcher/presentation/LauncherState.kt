package com.ralphmarondev.system.launcher.presentation

import com.ralphmarondev.system.launcher.domain.model.MiniApp

sealed class NavigationTarget {
    object None : NavigationTarget()
    object Settings : NavigationTarget()
    object Notes : NavigationTarget()
    object Clock : NavigationTarget()
    object Weather : NavigationTarget()
}

data class LauncherState(
    val miniApps: List<MiniApp> = emptyList(),
    val navigationTarget: NavigationTarget = NavigationTarget.None,
    val pageCount: Int = 2,
    val wallpaper: Int = 1
)
