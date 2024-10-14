package com.example.core.domain.usecase

import android.util.Log
import androidx.paging.PagingData
import com.example.core.Result
import com.example.core.domain.model.Game
import com.example.core.domain.repository.IGameRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetGameListInteractor @Inject constructor(
    private val gameRepository: IGameRepository
): GetGameListUseCase {
    override suspend fun getGameList(query: String?): Result<PagingData<Game>> {
        try {
            val gameList = gameRepository.getGameList(query).first()
            return Result.Success(gameList)
        }catch (e: Exception){
            Log.d("error", "${this.javaClass.simpleName}: ${e.message}")
            return Result.Error(e.message.toString())
        }
    }
}