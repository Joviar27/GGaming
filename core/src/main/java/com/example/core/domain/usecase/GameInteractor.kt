package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.Result
import com.example.core.data.GameRepository
import com.example.core.domain.model.Game
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameInteractor @Inject constructor(
    private val gameRepository: GameRepository
): GameUseCase {

    override suspend fun getGameList(query: String?): Flow<PagingData<Game>> {
        return gameRepository.getGameList(query)
    }

    override suspend fun getGameDetail(id: String): Flow<Result<Game>> {
        return gameRepository.getGameDetail(id)
    }
}