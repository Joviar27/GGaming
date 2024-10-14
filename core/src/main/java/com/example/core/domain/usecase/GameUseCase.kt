package com.example.core.domain.usecase

import com.example.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameUseCase {

    fun getGameList(query: String?): Flow<Game>

}