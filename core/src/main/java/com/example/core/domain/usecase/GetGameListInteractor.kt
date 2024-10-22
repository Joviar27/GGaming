package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.Result
import com.example.core.domain.model.Game
import com.example.core.domain.repository.IGameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameListInteractor @Inject constructor(
    private val gameRepository: IGameRepository
): GetGameListUseCase {
    override suspend fun getGameList(query: String?): Flow<Result<PagingData<Game>>> {
        return gameRepository.getGameList(query)
    }
}