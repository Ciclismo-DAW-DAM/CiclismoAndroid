package com.dam.ciclismoApp.models.endpoints

import com.dam.ciclismoApp.models.objects.LogIn
import com.dam.ciclismoApp.models.objects.LogInResponse
import com.dam.ciclismoApp.models.objects.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersApi {
    @POST("auth/login")
    suspend fun logIn(
        @Body logIn: LogIn
    ): LogInResponse

    @GET("user/{id}")
    suspend fun getUser(
        @Path("id") id:Int
    ): User

    @POST("user/{id}")
    suspend fun updUser(
        @Path("id") user:User
    ): Boolean

    @DELETE("user/{id}")
    suspend fun delUser(
        @Path("id") user:User
    )
}