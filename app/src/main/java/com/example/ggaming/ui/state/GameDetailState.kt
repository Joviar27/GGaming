package com.example.ggaming.ui.state

import com.example.core.domain.model.Game

data class GameDetailState(
    val game: Game? = null,
    val loading: Boolean = false,
    val error: Boolean = false,
    val errorMessage: String = ""
)