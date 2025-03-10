package com.dam.ciclismoApp.models.endpoints

import com.dam.ciclismoApp.models.objects.Participant
import com.dam.ciclismoApp.models.objects.Registration
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ParticipantsApi {
    @POST("cycling_participant/new")
    suspend fun addParticipant(
        @Body registration: Registration
    ):Participant

    @DELETE("cycling_participant")
    suspend fun removeParticipant(
        @Body registration: Registration
    ):Participant
}