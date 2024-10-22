package com.example.core.domain.usecase

import com.example.core.Result
import com.example.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GetGameDetailUseCase {
    suspend fun getGameDetail(
        name: String
    ): Flow<Result<Game>>
}