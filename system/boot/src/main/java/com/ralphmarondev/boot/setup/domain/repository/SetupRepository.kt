package com.ralphmarondev.boot.setup.domain.repository

import com.ralphmarondev.boot.setup.domain.model.SetupResult

interface SetupRepository {
    suspend fun setup(setupResult: SetupResult)
}