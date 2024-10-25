package com.example.core.data.remote.network

import com.example.core.data.remote.response.GameDetailResponse
import com.example.core.data.remote.response.GameListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun getGameList(
        @Query("search") search: String?,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
    ): GameListResponse

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: String,
    ): GameDetailResponse?
}