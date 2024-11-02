package com.example.core.domain.usecase

import com.example.core.Result
import com.example.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface FavoriteGameUseCase {
    fun getFavoriteGameList(): Flow<Result<List<Game>>>
    fun insertFavoriteGame(game: Game): Flow<Result<Unit>>
    fun removeFavoriteGame(gameId: String): Flow<Result<Unit>>
    fun getFavoriteStatus(gameId: String): Flow<Result<Boolean>>
}