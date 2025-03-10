package com.dam.ciclismoApp.models.objects

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@JsonClass(generateAdapter = true)
data class User(
    var id:Int,
    var email:String,
    var roles:List<String>,
    var name:String,
    var password:String?,
    var banned:Boolean,
    @Json(name = "cyclingParticipants") val cyclingParticipants:List<Participant>,
    var age:Int,
    var gender:String,
    var image:String,
    var oldpassword:String?,
    var newpassword:String?
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
        image = "",
        oldpassword = "",
        newpassword = ""
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
