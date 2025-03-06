package com.dam.ciclismoApp.models.endpoints

import com.dam.ciclismoApp.models.objects.LogIn
import com.dam.ciclismoApp.models.objects.LogInResponse
import com.dam.ciclismoApp.models.objects.User
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersApi {
    @POST("auth/login")
    suspend fun logIn(
        @Body logIn: LogIn
    ): User
}