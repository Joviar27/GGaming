package com.example.core.domain.model

import java.time.LocalDate
import java.util.Date

data class Game (
    val id: Int,
    val isFavorite: Boolean,
    val name: String,
    val releaseDate: Date = Date(),
    val rating: Double,
    val ratingCount: Int,
    val metacritic: Int,
    val background: String,
    val screenShoots: List<ScreenShoot>,
    val genres: List<Genre>,
    val stores: List<Store>,
    val platforms: List<Platform>
)

data class ScreenShoot(
    val id: Int,
    val image: String
)

data class Genre(
    val id: Int,
    val name: String
)

data class Store(
    val id: Int,
    val name: String,
    val domain: String,
    val image: String
)

data class Platform(
    val id: Int,
    val name: String,
    val image: String
)

fun createDummyGame(id: Int = 0) = Game(
    id = id,
    isFavorite = false,
    name = "Lorem Ipsum Dolor",
    releaseDate = Date(),
    rating = 4.44,
    ratingCount = 6777,
    metacritic = 87,
    background = "",
    screenShoots = listOf(ScreenShoot(
        id = 0,
        image = ""
    )),
    genres = listOf(Genre(
        id = 0,
        name = "Action"
    ),Genre(
        id = 1,
        name = "Adventure"
    ),Genre(
        id = 2,
        name = "Romance"
    )),
    stores = listOf(Store(
        id = 0,
        name = "Steam",
        domain = "",
        image = ""
    ),Store(
        id = 0,
        name = "Epic Game Store",
        domain = "",
        image = ""
    )),
    platforms = listOf(Platform(
        id = 0,
        name = "PS5",
        image = ""
    ),Platform(
        id = 1,
        name = "XBOX",
        image = ""
    ),Platform(
        id = 0,
        name = "Nintendo Switch",
        image = ""
    ))
)

fun createDummyGameList(amount:Int): List<Game>{
    val gameList = mutableListOf<Game>()
    for (i in 0..amount) {
        gameList.add(createDummyGame(i))
    }
    return gameList
}