package com.dam.ciclismoApp.models.repositories

import com.dam.ciclismoApp.models.endpoints.CyclingApi
import com.dam.ciclismoApp.models.objects.Race
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class CyclingRepository {
    val cyclingApi:CyclingApi
    init {
        cyclingApi = Retrofit.Builder()
            .baseUrl("http://192.168.40.87:8000/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    suspend fun getRaces():List<Race> = cyclingApi.getRaces()
    suspend fun getRace(id:Int): Race = cyclingApi.getRace(id)
}