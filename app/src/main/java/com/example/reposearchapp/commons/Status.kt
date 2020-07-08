package com.example.reposearchapp.commons

/**
 * A generic class that describes a data with a status success, progress or error.
 * For usage in the live data.
 */
sealed class Status<D, E> {
    class Success<D, E>(val data: D) : Status<D, E>()
    class Progress<D, E> : Status<D, E>()
    class Error<D, E>(val error: E) : Status<D, E>()
}