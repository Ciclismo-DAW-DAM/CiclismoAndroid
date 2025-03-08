package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class User(
    val id:Int,
    val email:String,
    val roles:List<String>,
    val name:String,
    val password:String?,
    val banned:Boolean,
    @Json(name = "cyclingParticipants") val cyclingParticipants:List<Participant>,
    val age:Int,
    val gender:String,
    val image:String
):Serializable
