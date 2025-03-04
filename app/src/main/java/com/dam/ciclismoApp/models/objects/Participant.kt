package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.util.Date

@JsonClass(generateAdapter = true)
data class Participant(
    val id:Int,
    val user_id:Int,
    val race_id:Int,
    val time:Date,
    val dorsal:Int,
    val banned:Boolean
):Serializable
