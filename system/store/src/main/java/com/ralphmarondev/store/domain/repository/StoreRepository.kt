package com.ralphmarondev.store.domain.repository

import com.ralphmarondev.core.domain.model.LumiApp

interface StoreRepository {
    suspend fun loadApplications(): List<LumiApp>
}