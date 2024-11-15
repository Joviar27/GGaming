package com.example.core.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
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
import com.example.core.data.local.LocalDataSource
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

@Singleton
class GameRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IGameRepository{

    @OptIn(ExperimentalPagingApi::class)
    override fun getGameList(query: String?): Flow<PagingData<Game>>{
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE
        )
        return Pager(
            config = pagingConfig,
            remoteMediator = GameRemoteMediator(
                query = query,
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource
            )
        ){
            localDataSource.getGames()
        }.flow.map { pagingData ->
            pagingData.map {
                DataMapper.mapEntityToDomain(it)
            }
        }
    }

    override fun getGameDetail(id: String): Flow<Result<Game>> = flow {
        try {
            val gameDetailResponse = remoteDataSource.getGameDetail(id)
            if(gameDetailResponse!=null){
                emit(Result.Success(DataMapper.mapDetailResponseToDomain(gameDetailResponse, false)))
            }else throw IllegalArgumentException("Unable to find game detail")
        }catch (e: Exception){
            Log.d("error", "getGameDetail ${this@GameRepository.javaClass.simpleName}: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
    }

    override fun getFavoriteStatus(gameId: String): Flow<Result<Boolean>> = flow{
        try {
            val isFavorite = localDataSource.isGameFavorite(gameId).map {
                Result.Success(it)
            }
            emitAll(isFavorite)
        }catch (e: Exception){
            Log.d("error", "getFavoriteStatus ${this@GameRepository.javaClass.simpleName}: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
    }

    override fun getFavoriteGameList(): Flow<Result<List<Game>>> = flow{
        try {
            val favoriteGames = localDataSource.getFavoriteGames().map { gameList ->
                Result.Success(gameList.map {
                    DataMapper.mapFavoriteEntityToDomain(it)
                })
            }
            emitAll(favoriteGames)
        }catch (e: Exception){
            Log.d("error", "getFavoriteGames ${this@GameRepository.javaClass.simpleName}: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
    }

    override fun insertFavoriteGame(game: Game) : Flow<Result<Unit>> = flow{
        try {
            val result = localDataSource.insertFavoriteAndUpdate(
                DataMapper.mapFavoriteDomainToEntity(game)
            )
            emit(Result.Success(result))
        }catch (e: Exception){
            Log.d("error", "insertFavoriteGame ${this@GameRepository.javaClass.simpleName}: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
    }

    override fun removeFavoriteGame(gameId: String) : Flow<Result<Unit>> = flow {
        try {
            val result = localDataSource.deleteFavoriteAndUpdate(gameId)
            emit(Result.Success(result))
        }catch (e: Exception){
            Log.d("error", "removeFavoriteGame ${this.javaClass.simpleName}: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object{
        private const val PAGE_SIZE = 5
    }
}