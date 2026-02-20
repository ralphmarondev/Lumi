package com.ralphmarondev.store.data.repository

import com.ralphmarondev.core.data.local.database.dao.LumiAppDao
import com.ralphmarondev.core.data.local.database.mapper.toDomain
import com.ralphmarondev.core.domain.model.LumiApp
import com.ralphmarondev.store.domain.repository.StoreRepository

class StoreRepositoryImpl(
    private val lumiAppDao: LumiAppDao
) : StoreRepository {
    override suspend fun loadApplications(): List<LumiApp> {
        return lumiAppDao.getAll().map { it.toDomain() }
    }

    override suspend fun getAppById(id: Long): LumiApp? {
        return lumiAppDao.getById(id)?.toDomain()
    }

    override suspend fun installApp(id: Long) {
        lumiAppDao.install(id)
    }

    override suspend fun unInstallApp(id: Long) {
        lumiAppDao.unInstall(id)
    }
}