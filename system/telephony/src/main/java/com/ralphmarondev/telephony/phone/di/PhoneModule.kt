package com.ralphmarondev.telephony.phone.di

import com.ralphmarondev.telephony.phone.presentation.history.HistoryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val phoneModule = module {
    viewModelOf(::HistoryViewModel)
}