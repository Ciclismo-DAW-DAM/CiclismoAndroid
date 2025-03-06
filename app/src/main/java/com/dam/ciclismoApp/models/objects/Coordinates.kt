package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Coordinates(
    val lat:Double,
    val lng:Double
): Serializable
