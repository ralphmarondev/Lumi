package com.ralphmarondev.calculator.di

import com.ralphmarondev.calculator.presentation.calculate.CalculateViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val calculatorModule = module {
    viewModelOf(::CalculateViewModel)
}