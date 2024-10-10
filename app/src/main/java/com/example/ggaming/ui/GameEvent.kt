package com.example.ggaming.ui

import com.example.core.domain.model.Game

interface GameEvent {
    data class OnItemClicked(val game: Game): GameEvent
    data class OnFavoriteClicked(val game: Game): GameEvent
}