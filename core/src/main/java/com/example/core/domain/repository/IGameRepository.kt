package com.example.core.domain.repository

import androidx.paging.PagingData
import com.example.core.Result
import com.example.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface IGameRepository {
    fun getGameList(query: String?): Flow<PagingData<Game>>
    fun getGameDetail(id: String): Flow<Result<Game>>

    fun getFavoriteGameList(): Flow<Result<List<Game>>>
    fun insertFavoriteGame(game: Game): Flow<Result<Unit>>
    fun removeFavoriteGame(gameId: String): Flow<Result<Unit>>
    fun getFavoriteStatus(gameId: String): Flow<Result<Boolean>>
}