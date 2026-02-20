package com.ralphmarondev.store.di

import com.ralphmarondev.store.data.repository.StoreRepositoryImpl
import com.ralphmarondev.store.domain.repository.StoreRepository
import com.ralphmarondev.store.presentation.app_list.AppListViewModel
import com.ralphmarondev.store.presentation.details.DetailViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val storeModule = module {
    singleOf(::StoreRepositoryImpl).bind<StoreRepository>()
    viewModelOf(::AppListViewModel)
    viewModelOf(::DetailViewModel)
}