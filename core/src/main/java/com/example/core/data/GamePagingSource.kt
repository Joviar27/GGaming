package com.example.core.data

//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.core.data.local.LocalDataSource
//import com.example.core.data.local.entity.GameEntity
//import com.example.core.data.remote.RemoteDataSource
//import com.example.core.data.remote.response.GameItemResponse
//import com.example.core.utils.DataMapper
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.firstOrNull
//import kotlinx.coroutines.flow.flatMapLatest
//import kotlinx.coroutines.flow.flowOf
//
//class GamePagingSource(
//    private val query: String?,
//    private val remoteDataSource: RemoteDataSource,
//    private val localDataSource: LocalDataSource
//): PagingSource<Int, GameEntity>() {
//
//    override fun getRefreshKey(state: PagingState<Int, GameEntity>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val closestPage = state.closestPageToPosition(anchorPosition)
//            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
//        }
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameEntity> {
//        return try {
//            val currentPage = params.key ?: 1
//            val gameEntities = remoteDataSource.getGameList(
//                query,
//                currentPage,
//                params.loadSize
//            ).map {
//                val isFavorite = localDataSource.isGameFavorite(it.id).firstOrNull()
//                DataMapper.mapResponseToEntity(it, isFavorite ?: false)
//            }
//            val gameList = flowOf(localDataSource.insertGames(gameEntities)).flatMapLatest {
//                flowOf(localDataSource.getGames())
//            }.first()
//            LoadResult.Page(
//                data = gameList,
//                prevKey = if(currentPage==1) null else currentPage-1,
//                nextKey = if(gameList.isEmpty()) null else currentPage+1
//            )
//        }catch (e: Exception){
//            LoadResult.Error(e)
//        }
//    }
//}