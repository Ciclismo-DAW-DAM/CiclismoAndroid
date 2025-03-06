package com.dam.ciclismoApp.models.endpoints

import com.dam.ciclismoApp.models.objects.Participant
import retrofit2.http.GET

interface ParticipantsApi {
    @GET("cycling_participant")
    suspend fun getParticipants():List<Participant>
}