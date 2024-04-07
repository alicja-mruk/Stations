package com.example.myapp.core.domain.cache

import kotlinx.serialization.Serializable
import kotlin.Double.Companion.POSITIVE_INFINITY

@Serializable
sealed class CacheTime(open val minutes: Int) {
    @Serializable
    data object NoExpiration : CacheTime(POSITIVE_INFINITY.toInt())
    @Serializable
    data object Day : CacheTime(DAY_IN_MINUTES)
    data class Custom(override val minutes: Int) : CacheTime(minutes)
}

const val DAY_IN_MINUTES = 60 * 24