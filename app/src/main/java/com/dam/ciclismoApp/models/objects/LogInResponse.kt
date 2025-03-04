package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class LogInResponse(
    val token:String,
    val user: User
):Serializable