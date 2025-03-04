package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.util.Date

@JsonClass(generateAdapter = true)
data class Race(
    val id:Int,
    val name:String,
    @Json(name = "descripcion") val description:String,
    val date: Date,
    @Json(name = "distance_km") val distance:Double,
    val location:String,
    val coordinates:String,
    @Json(name = "entry_fee") val fee:Double,
    @Json(name = "available_slots") val slots:Int,
    val status:String,
    val category:String,
    val max_time:String,
    val image:String
):Serializable
