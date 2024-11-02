package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.Result
import com.example.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    suspend fun getGameList(
        query: String?
    ): Flow<PagingData<Game>>

    suspend fun getGameDetail(
        id: String
    ): Flow<Result<Game>>
}