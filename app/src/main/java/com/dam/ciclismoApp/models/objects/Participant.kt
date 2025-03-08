package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@JsonClass(generateAdapter = true)
data class Participant(
    val id:Int,
    val user:User,
    val race:Race,
    val time: String,
    val dorsal:Int,
    val banned:Boolean
):Serializable {
    fun getFechaComoOffsetDateTime(): OffsetDateTime =
        OffsetDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}

