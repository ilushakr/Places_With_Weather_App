package com.example.domain.common

sealed class Result<T>{
    data class Pending<T>(val data: T? = null): Result<T>()
    data class Success<T>(val data: T): Result<T>()
    data class Fail<T>(val error: Throwable): Result<T>()
}
