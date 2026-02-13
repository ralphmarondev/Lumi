package com.ralphmarondev.core.domain.model

data class CallHistory(
    val id: Long = 0,
    val owner: String = "",
    val name: String? = null,
    val number: String = "",
    val type: String = CallType.Outgoing.name,
    val date: Long = System.currentTimeMillis(),
    val duration: Long = 0
)

enum class CallType { Incoming, Outgoing }
