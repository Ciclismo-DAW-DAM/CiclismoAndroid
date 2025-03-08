package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
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

    fun toJson(): String {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(Participant::class.java)
        val json = adapter.toJson(this)
        return json
    }

    fun fromJson(json:String): Participant {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(Participant::class.java)
        val participante: Participant? = adapter.fromJson(json)
        return checkNotNull(participante)
    }

    fun getFechaComoOffsetDateTime(): OffsetDateTime =
        OffsetDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}

