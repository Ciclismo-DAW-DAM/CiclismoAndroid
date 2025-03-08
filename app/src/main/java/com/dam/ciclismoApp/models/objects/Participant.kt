package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@JsonClass(generateAdapter = true)
data class Participant(
    val id:Int,
    val user:User?,
    val race:Race,
    val time: String,
    val dorsal:Int,
    val banned:Boolean
):Serializable {
    constructor() : this(
        id = 0,
        user = User(),  // Asegúrate de que User tenga un constructor vacío o valores por defecto
        race = Race(),  // Asegúrate de que Race tenga un constructor vacío o valores por defecto
        time = "",
        dorsal = 0,
        banned = false
    )

    fun getFechaComoOffsetDateTime(): OffsetDateTime =
        OffsetDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}

