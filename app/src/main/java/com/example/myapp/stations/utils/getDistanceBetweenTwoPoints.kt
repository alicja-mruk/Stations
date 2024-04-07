package com.example.myapp.stations.utils

import android.location.Location

data class LatLng(
    val latitude: Double,
    val longitude: Double
)

typealias Km = Number

/**
Returns distance between two points in Kilometers
 */
fun getDistanceBetweenTwoPoints(
    startPoint: LatLng, endPoint: LatLng
): Km {
    val locationStart = Location("").apply {
        latitude = startPoint.latitude
        longitude = startPoint.longitude
    }
    val locationEnd = Location("").apply {
        latitude = endPoint.latitude
        longitude = endPoint.longitude
    }

    return (locationStart.distanceTo(locationEnd) / METERS_IN_KM).roundToDecimals()
}

const val INVALID_VALUE = -1

private const val METERS_IN_KM = 1000