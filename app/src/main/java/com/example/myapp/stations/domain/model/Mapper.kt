package com.example.myapp.stations.domain.model

interface Mapper<T : Any, Model : Any> {
    fun toModel(value: T): Model
    fun fromModel(value: Model): T
}