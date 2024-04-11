package com.elguille.purrfectpics.domain

typealias DomainError = Error
sealed interface Resource<out D, out E> {
    class Empty<out D, out E> : Resource<D, E>
    class Loading<out D, out E> : Resource<D, E>
    class Success<out D, out E>(val data: D) : Resource<D, E>
    class Error<out D, out E>(val error: DomainError) : Resource<D, E>
}