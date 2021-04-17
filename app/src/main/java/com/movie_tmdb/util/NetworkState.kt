package com.movie_tmdb.util

/**
 * sealed class to maintain network responses
 * */
sealed class NetworkState{
    data class Success(val status: Boolean): NetworkState()
    data class Failure(val viewType: ErrorViewTypes, val exception : Throwable) : NetworkState()
    data class Loading(val status: Boolean): NetworkState()
}
/**
 * enum to provide options of view to inflate in case of network request failure
 * */
enum class ErrorViewTypes{
    TOAST, SNACK_BAR, DIALOG
}