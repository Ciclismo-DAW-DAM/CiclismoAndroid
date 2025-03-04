package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class User(
    val id:Int,
    val name:String,
    val email:String,
    val role:List<String>,
    val banned:Boolean
):Serializable
