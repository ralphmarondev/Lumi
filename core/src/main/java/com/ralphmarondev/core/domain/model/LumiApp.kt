package com.ralphmarondev.core.domain.model

data class LumiApp(
    val id: Long = 0,
    val name: String = "",
    val icon: String = "",
    val versionName: String = "",
    val versionCode: Int = 1,
    val isSystemApp: Boolean = false,
    val isDocked: Boolean = false,
    val isInstalled: Boolean = true,
    val order: Int = 0,
    val tag: String = ""
)