package com.example.core

sealed class Result<out T> private constructor() {
    data class Success<out T>(val data : T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}