package com.dam.ciclismoApp.models.endpoints

import com.dam.ciclismoApp.models.objects.Participant
import com.dam.ciclismoApp.models.objects.Registration
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ParticipantsApi {
    @POST("cycling_participant/new")
    suspend fun addParticipant(
        @Header("Authorization") token:String,
        @Body registration: Registration
    ):Participant
}