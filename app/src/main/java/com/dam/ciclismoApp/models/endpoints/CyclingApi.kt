package com.dam.ciclismoApp.models.endpoints

import com.dam.ciclismoApp.models.objects.Race
import retrofit2.http.GET
import retrofit2.http.Path

interface CyclingApi {
    @GET("cycling")
    suspend fun getRaces():List<Race>

    @GET("cycling/{id}")
    suspend fun getRace(
        @Path("id") id:Int
    ): Race
}