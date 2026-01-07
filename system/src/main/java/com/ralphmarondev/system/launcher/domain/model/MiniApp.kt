package com.ralphmarondev.system.launcher.domain.model

data class MiniApp(
    val id: Int = 0,
    val name: String,
    val image: Int,
    val onClick: () -> Unit
)
