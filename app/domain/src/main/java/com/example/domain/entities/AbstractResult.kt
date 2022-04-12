package com.example.domain.entities

abstract class AbstractResult<T: Any> {

    abstract fun mapUi(): T

}