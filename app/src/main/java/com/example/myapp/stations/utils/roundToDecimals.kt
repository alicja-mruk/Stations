package com.example.myapp.stations.utils

fun Number.roundToDecimals(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()