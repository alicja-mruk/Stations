package com.example.myapp.stations.utils

import android.location.Location

data class LatLng(
    val latitude: Double,
    val longitude: Double
)

val Double.kilometers: Kilometers
    get() = this.kilometers

@JvmInline
value class Kilometers(val value: Double) {
    operator fun plus(other: Kilometers) = this.value + other.value
    override fun toString(): String {
        return this.value.toString()
    }
}

/**
Returns distance between two points in Kilometers
 */
fun getDistanceBetweenTwoPoints(
    startPoint: LatLng, endPoint: LatLng
): Kilometers {
    val locationStart = Location("").apply {
        latitude = startPoint.latitude
        longitude = startPoint.longitude
    }
    val locationEnd = Location("").apply {
        latitude = endPoint.latitude
        longitude = endPoint.longitude
    }

    val kilometers = (locationStart.distanceTo(locationEnd) / METERS_IN_KM).roundToDecimals()
    return Kilometers(value = kilometers)
}

private const val METERS_IN_KM = 1000