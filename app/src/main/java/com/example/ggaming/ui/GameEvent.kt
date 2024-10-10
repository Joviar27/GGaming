package com.example.ggaming.ui

import com.example.core.domain.model.Game

interface GameEvent {
    data class OnItemClick(val game: Game): GameEvent
}