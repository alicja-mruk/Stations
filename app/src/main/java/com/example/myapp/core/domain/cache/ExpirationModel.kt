package com.example.myapp.core.domain.cache

interface ExpirationModel {
    val createdAt: Long
    val cacheTime: CacheTime
}