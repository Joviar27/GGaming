package com.example.core.data.local

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.core.data.local.entity.FavoriteGameEntity
import com.example.core.data.local.entity.GameEntity
import com.example.core.data.local.entity.RemoteKeyEntity
import com.example.core.data.local.room.FavoriteGameDao
import com.example.core.data.local.room.GameDao
import com.example.core.data.local.room.GameDatabase
import com.example.core.data.local.room.RemoteKeyDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val gameDatabase: GameDatabase,
    private val favoriteGameDao: FavoriteGameDao,
    private val gameDao: GameDao,
    private val remoteKeyDao: RemoteKeyDao
) {
    fun getGames(): PagingSource<Int, GameEntity>{
        return gameDao.getGames()
    }

    suspend fun insertGames(gameEntities: List<GameEntity>){
        return gameDao.insertGames(gameEntities)
    }

    suspend fun deleteAllGames(){
        return gameDao.deleteAllGames()
    }

    suspend fun insertRemoteKeys(keys: List<RemoteKeyEntity>){
        return remoteKeyDao.insertKeys(keys)
    }

    suspend fun getRemoteKey(id: String): RemoteKeyEntity?{
        return remoteKeyDao.getById(id)
    }

    suspend fun deleteRemoteKeys(){
        return remoteKeyDao.deleteRemoteKeys()
    }

    fun getFavoriteGames() : Flow<List<FavoriteGameEntity>> {
        return favoriteGameDao.getFavoriteGames()
    }

    suspend fun insertFavoriteGame(favoriteGameEntity: FavoriteGameEntity){
        return favoriteGameDao.insertFavoriteGame(favoriteGameEntity)
    }

    suspend fun deleteFavoriteGame(gameId: String){
        return favoriteGameDao.deleteFavoriteGame(gameId)
    }

    fun isGameFavorite(gameId: Int): Flow<Boolean>{
        return favoriteGameDao.isGameFavorite(gameId)
    }
}