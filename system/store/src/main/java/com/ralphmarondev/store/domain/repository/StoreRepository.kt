package com.ralphmarondev.store.domain.repository

import com.ralphmarondev.core.domain.model.LumiApp

interface StoreRepository {
    suspend fun loadApplications(): List<LumiApp>
    suspend fun getAppById(id: Long): LumiApp?
    suspend fun installApp(id: Long)
    suspend fun unInstallApp(id: Long)
}