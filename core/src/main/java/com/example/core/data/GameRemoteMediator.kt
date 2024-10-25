package com.example.core.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.core.data.local.LocalDataSource
import com.example.core.data.local.entity.GameEntity
import com.example.core.data.local.entity.RemoteKeyEntity
import com.example.core.data.local.room.GameDatabase
import com.example.core.data.remote.RemoteDataSource
import com.example.core.data.remote.response.GameItemResponse
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.firstOrNull

@OptIn(ExperimentalPagingApi::class)
class GameRemoteMediator(
    private val query: String?,
    private val gameDatabase: GameDatabase,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): RemoteMediator<Int, GameEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GameEntity>
    ): MediatorResult {
        val page = when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND ->{
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND ->{
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val gameEntities = remoteDataSource.getGameList(
                query,
                page,
                state.config.pageSize
            )?.map {
                val isFavorite = localDataSource.isGameFavorite(it.id).firstOrNull()
                DataMapper.mapResponseToEntity(it, isFavorite ?: false)
            } ?: emptyList()

            val endOfPaginationReached = gameEntities.isEmpty()

            gameDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localDataSource.deleteAllGames()
                    localDataSource.deleteRemoteKeys()
                }

                val prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val remoteKeys = gameEntities.map {
                    RemoteKeyEntity(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                localDataSource.insertGames(gameEntities)
                localDataSource.insertRemoteKeys(remoteKeys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e: Exception){
            Log.d("error", "load ${this.javaClass.simpleName}: ${e.message}")
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GameEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { data ->
            localDataSource.getRemoteKey(data.id.toString())
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GameEntity>): RemoteKeyEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { data ->
            localDataSource.getRemoteKey(data.id.toString())
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GameEntity>): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                localDataSource.getRemoteKey(id.toString())
            }
        }
    }

    private companion object{
        const val INITIAL_PAGE_INDEX = 1
        //const val REMOTE_KEY_ID = "game-key"
    }


}