package com.example.loginclean.data

sealed class ResourceFirebase<out T> {
    object Loading: ResourceFirebase<Nothing>()

    data class Success<out T>(
        val data: T
    ): ResourceFirebase<T>()

    data class Failure(
        val errorMessage: String
    ): ResourceFirebase<Nothing>()
}