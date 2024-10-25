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
import com.example.core.data.local.room.GameDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

@Singleton
class GameRepository @Inject constructor(
    private val gameDatabase: GameDatabase,
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
                gameDatabase = gameDatabase,
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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getGameDetail(id: String): Flow<Result<Game>> = flow {
        try {
            val gameDetailResponse = remoteDataSource.getGameDetail(id)
            if(gameDetailResponse!=null){
                val result = localDataSource.isGameFavorite(gameDetailResponse.id).flatMapLatest {
                    flowOf(Result.Success(DataMapper.mapDetailResponseToDomain(gameDetailResponse, it)))
                }
                emitAll(result)
            }else throw IllegalArgumentException("Unable to find game detail")
        }catch (e: Exception){
            Log.d("error", "getGameDetail ${this@GameRepository.javaClass.simpleName}: ${e.message}")
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
            val result = localDataSource.insertFavoriteGame(
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
            val result = localDataSource.deleteFavoriteGame(gameId)
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