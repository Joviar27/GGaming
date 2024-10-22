package com.example.core.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.core.data.remote.RemoteDataSource
import com.example.core.domain.model.Game
import com.example.core.domain.repository.IGameRepository
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import com.example.core.Result
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

@Singleton
class GameRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : IGameRepository{

    override fun getGameList(query: String?): Flow<Result<PagingData<Game>>> = flow{
        try {
            val pagingConfig = PagingConfig(
                pageSize = PAGE_SIZE
            )
            val pagingDataFlow =  Pager(
                config = pagingConfig
            ){
                GamePagingSource(query, remoteDataSource)
            }.flow
            val result = pagingDataFlow.map { pagingData ->
                Result.Success(pagingData.map {
                    DataMapper.mapResponseToDomain(it, false)
                })
            }
            emitAll(result)
        }catch (e: Exception){
            Log.d("error", "${this.javaClass.simpleName}: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object{
        private const val PAGE_SIZE = 10
    }
}