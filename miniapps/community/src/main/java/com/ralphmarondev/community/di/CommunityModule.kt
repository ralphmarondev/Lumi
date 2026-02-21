package com.ralphmarondev.community.di

import com.ralphmarondev.community.auth.di.authModule
import org.koin.dsl.module

val communityModule = module {
    includes(authModule)
}