package com.example.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.remote.RemoteDataSource
import com.example.core.data.remote.response.GameItemResponse

class GamePagingSource(
    private val query: String?,
    private val remoteDataSource: RemoteDataSource,
): PagingSource<Int, GameItemResponse>() {

    override fun getRefreshKey(state: PagingState<Int, GameItemResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val closestPage = state.closestPageToPosition(anchorPosition)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameItemResponse> {
        return try {
            val currentPage = params.key ?: 1
            val gameList = remoteDataSource.getGameList(
                query,
                currentPage,
                params.loadSize
            )
            LoadResult.Page(
                data = gameList,
                prevKey = if(currentPage==1) null else currentPage-1,
                nextKey = if(gameList.isEmpty()) null else currentPage+1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}