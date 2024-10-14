package com.example.ggaming.ui

import androidx.paging.PagingData
import com.example.core.domain.model.Game

data class PagingGameState(
    val loading: Boolean = false,
    val pagingGameList: PagingData<Game> = PagingData.empty(),
)