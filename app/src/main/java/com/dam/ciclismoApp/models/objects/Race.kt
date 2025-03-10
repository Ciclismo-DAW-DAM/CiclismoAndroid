package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
@JsonClass(generateAdapter = true)
data class Race(
    val id: Int,
    val name: String,
    val description: String,
    val date: String,
//    @Json(name = "distance_km")
    val distance: Int,
    val location: String,
    val coordinates: Coordinates, // Ahora es un objeto en lugar de String
    val unevenness: Int,
//    @Json(name = "entry_fee")
    val fee: Int,
//    @Json(name = "available_slots")
    val slots: Int,
    val status: String,
    val category: String,
    val image: String,
//    @Json(name = "cyclingParticipants")
    val participants: List<Participant> = emptyList()
) : Serializable {
    constructor() : this(
        id = 0,
        name = "",
        description = "",
        date = "",
        distance = 0,
        location = "",
        coordinates = Coordinates(0.0,0.0), // Usa la clase de coordenadas con valores por defecto
        unevenness = 0,
        fee = 0,
        slots = 0,
        status = "",
        category = "",
        image = "",
        participants = emptyList()
    )

    fun toJson(): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Race::class.java)
        return adapter.toJson(this)
    }

    fun fromJson(json: String): Race {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Race::class.java)
        return checkNotNull(adapter.fromJson(json))
    }

    fun getFechaComoOffsetDateTime(): OffsetDateTime =
        OffsetDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}