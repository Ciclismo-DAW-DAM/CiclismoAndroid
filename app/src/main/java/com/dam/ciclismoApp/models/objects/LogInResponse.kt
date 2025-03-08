package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class LogInResponse(
    val message:String,
    val user: User
):Serializable

