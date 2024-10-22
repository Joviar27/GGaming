package com.example.core.domain.usecase

import com.example.core.Result
import com.example.core.data.GameRepository
import com.example.core.domain.model.Game
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameDetailInteractor @Inject constructor(
    private val gameRepository: GameRepository
): GetGameDetailUseCase {
    override suspend fun getGameDetail(name: String): Flow<Result<Game>> {
        return gameRepository.getGameDetail(name)
    }
}