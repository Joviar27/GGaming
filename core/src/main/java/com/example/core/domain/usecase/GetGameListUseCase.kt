package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.Result
import com.example.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GetGameListUseCase {
    suspend fun getGameList(
        query: String?
    ): Flow<Result<PagingData<Game>>>
}