package com.example.core.domain.repository

import androidx.paging.PagingData
import com.example.core.Result
import com.example.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface IGameRepository {
    fun getGameList(query: String?): Flow<Result<PagingData<Game>>>
    fun getGameDetail(name: String?): Flow<Result<Game>>
}