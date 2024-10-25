package com.example.core.data.remote

import com.example.core.data.remote.network.ApiService
import com.example.core.data.remote.response.GameDetailResponse
import com.example.core.data.remote.response.GameItemResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getGameList(
        search: String?,
        page: Int,
        pageSize: Int
    ): List<GameItemResponse>?{
        return apiService.getGameList(search, page, pageSize).results
    }

    suspend fun getGameDetail(
        gameId: String
    ): GameDetailResponse?{
        return apiService.getGameDetail(gameId)
    }
}