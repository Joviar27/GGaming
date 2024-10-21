package com.example.ggaming.ui

import com.example.core.domain.model.Game

data class GameState(
    val gameList: List<Game> = listOf(),
    val error: Boolean = false,
    val errorMessage: String = ""
)