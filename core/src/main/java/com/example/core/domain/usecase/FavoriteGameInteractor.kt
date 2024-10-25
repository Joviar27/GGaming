package com.example.core.domain.usecase

import com.example.core.Result
import com.example.core.data.GameRepository
import com.example.core.domain.model.Game
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteGameInteractor @Inject constructor(
    private val gameRepository: GameRepository
): FavoriteGameUseCase {

    override fun getFavoriteGameList(): Flow<Result<List<Game>>> {
        return gameRepository.getFavoriteGameList()
    }

    override fun insertFavoriteGame(game: Game): Flow<Result<Unit>> {
        return gameRepository.insertFavoriteGame(game)
    }

    override fun removeFavoriteGame(gameId: String): Flow<Result<Unit>> {
        return gameRepository.removeFavoriteGame(gameId)
    }
}