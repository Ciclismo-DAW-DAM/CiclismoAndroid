package com.dam.ciclismoApp.models.repositories

import com.dam.ciclismoApp.models.endpoints.UsersApi
import com.dam.ciclismoApp.models.objects.LogIn
import com.dam.ciclismoApp.models.objects.LogInResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class UsersRepository {
    val usersApi: UsersApi
    init {
        usersApi = Retrofit.Builder()
            .baseUrl("localhost/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    suspend fun logIn(logIn: LogIn): LogInResponse = usersApi.logIn(logIn)
}