package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.Result
import com.example.core.domain.model.Game

interface GetGameListUseCase {
    suspend fun getGameList(
        query: String?
    ): Result<PagingData<Game>>
}