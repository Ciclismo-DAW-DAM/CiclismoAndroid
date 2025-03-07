package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@JsonClass(generateAdapter = true)
data class Race(
    val id:Int,
    val name:String,
    val description:String,
    val date: String,
    @Json(name = "distance_km") val distance:Double,
    val location:String,
    val coordinates:String,
    val unevenness:Int,
    @Json(name = "entry_fee") val fee:Double,
    @Json(name = "available_slots") val slots:Int,
    val status:String,
    val category:String,
    val image:String,
    @Json(name = "cyclingParticipants") val participants:List<Participant> = emptyList()
):Serializable {
    fun getFechaComoOffsetDateTime(): OffsetDateTime =
        OffsetDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}
