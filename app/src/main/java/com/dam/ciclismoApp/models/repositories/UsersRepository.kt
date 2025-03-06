package com.dam.ciclismoApp.models.repositories

import com.dam.ciclismoApp.models.endpoints.UsersApi
import com.dam.ciclismoApp.models.objects.LogIn
import com.dam.ciclismoApp.models.objects.LogInResponse
import com.dam.ciclismoApp.models.objects.User
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.time.OffsetDateTime

class UsersRepository {
    val usersApi: UsersApi
    init {
        usersApi = Retrofit.Builder()
            .baseUrl("http://192.168.40.87:8000/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    suspend fun logIn(logIn: LogIn): User = usersApi.logIn(logIn)
}