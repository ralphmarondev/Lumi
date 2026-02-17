package com.ralphmarondev.launcher.presentation

import com.ralphmarondev.core.domain.model.LumiApp
import com.ralphmarondev.core.domain.model.LumiAppTag

data class LauncherState(
    val dockLumiApps: List<LumiApp> = emptyList(),
    val miniLumiApps: List<LumiApp> = emptyList(),
    val launchLumiApp: LumiAppTag = LumiAppTag.Unknown,
    val pageCount: Int = 2,
    val wallpaper: String = ""
)