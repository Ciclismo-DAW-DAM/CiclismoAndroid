package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory
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
):Serializable {
    constructor() : this(
        id = 0,
        email = "",
        roles = emptyList(),
        name = "",
        password = "",
        banned = false,
        cyclingParticipants = emptyList(),
        age = 0,
        gender = "",
        image = ""
    )
    fun toJson(): String {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(User::class.java)
        val json = adapter.toJson(this)
        return json
    }

    fun fromJson(json:String): User {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(User::class.java)
        val usuario: User? = adapter.fromJson(json)
        return checkNotNull(usuario)
    }
}
