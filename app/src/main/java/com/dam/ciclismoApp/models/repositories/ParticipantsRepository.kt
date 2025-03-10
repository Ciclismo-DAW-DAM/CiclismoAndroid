package com.dam.ciclismoApp.models.repositories

import com.dam.ciclismoApp.models.endpoints.ParticipantsApi
import com.dam.ciclismoApp.models.objects.Participant
import com.dam.ciclismoApp.models.objects.Registration
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.Header

class ParticipantsRepository {
    val participantsApi:ParticipantsApi
    init {
        participantsApi = Retrofit.Builder()
            .baseUrl("http://192.168.0.17:8000/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    suspend fun addParticipant(@Body registration: Registration):Participant = participantsApi.addParticipant(registration)
    suspend fun removeParticipant(@Body registration: Registration):Participant = participantsApi.removeParticipant(registration)
}