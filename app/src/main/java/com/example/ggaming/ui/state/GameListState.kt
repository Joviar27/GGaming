package com.example.ggaming.ui.state

import com.example.core.domain.model.Game

data class GameListState(
    val gameList: List<Game> = listOf(),
    val loading: Boolean = false,
    val error: Boolean = false,
    val errorMessage: String = ""
)