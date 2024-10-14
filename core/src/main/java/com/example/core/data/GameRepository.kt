package com.example.core.data

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

class GameRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : IGameRepository{

    override fun getGameList(query: String?): Flow<PagingData<Game>> {
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE
        )
        val pagingDataFlow =  Pager(
            config = pagingConfig
        ){
            GamePagingSource(query, remoteDataSource)
        }.flow
        return pagingDataFlow.map { pagingData ->
            pagingData.map {
                DataMapper.mapResponseToDomain(it, false)
            }
        }
    }

    companion object{
        private const val PAGE_SIZE = 10
    }
}