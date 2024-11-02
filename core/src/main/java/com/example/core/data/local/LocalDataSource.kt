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
    suspend fun updatePagingGames(
        loadType: LoadType,
        gameEntities: List<GameEntity>,
        remoteKeys: List<RemoteKeyEntity>
    ){
        return gameDatabase.withTransaction {
            if (loadType == LoadType.REFRESH) {
                deleteAllGames()
                deleteRemoteKeys()
            }
            insertGames(gameEntities)
            insertRemoteKeys(remoteKeys)
        }
    }

    fun getGames(): PagingSource<Int, GameEntity>{
        return gameDao.getGames()
    }

    private suspend fun insertGames(gameEntities: List<GameEntity>){
        return gameDao.insertGames(gameEntities)
    }

    private suspend fun deleteAllGames(){
        return gameDao.deleteAllGames()
    }

    private suspend fun updateFavoriteStatus(gameId: String, isFavorite: Boolean){
        return gameDao.updateFavoriteStatus(gameId, isFavorite)
    }

    private suspend fun insertRemoteKeys(keys: List<RemoteKeyEntity>){
        return remoteKeyDao.insertKeys(keys)
    }

    suspend fun getRemoteKey(id: String): RemoteKeyEntity?{
        return remoteKeyDao.getById(id)
    }

    private suspend fun deleteRemoteKeys(){
        return remoteKeyDao.deleteRemoteKeys()
    }

    fun getFavoriteGames() : Flow<List<FavoriteGameEntity>> {
        return favoriteGameDao.getFavoriteGames()
    }

    private suspend fun insertFavoriteGame(favoriteGameEntity: FavoriteGameEntity){
        return favoriteGameDao.insertFavoriteGame(favoriteGameEntity)
    }

    private suspend fun deleteFavoriteGame(gameId: String){
        return favoriteGameDao.deleteFavoriteGame(gameId)
    }

    fun isGameFavorite(gameId: String): Flow<Boolean>{
        return favoriteGameDao.isGameFavorite(gameId)
    }

    suspend fun insertFavoriteAndUpdate(favoriteGameEntity: FavoriteGameEntity){
        gameDatabase.withTransaction {
            insertFavoriteGame(favoriteGameEntity)
            updateFavoriteStatus(favoriteGameEntity.id, true)
        }
    }

    suspend fun deleteFavoriteAndUpdate(gameId: String){
        gameDatabase.withTransaction {
            deleteFavoriteGame(gameId)
            updateFavoriteStatus(gameId, false)
        }
    }
}