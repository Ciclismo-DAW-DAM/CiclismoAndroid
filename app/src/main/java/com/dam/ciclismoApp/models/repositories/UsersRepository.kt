package com.dam.ciclismoApp.models.repositories

import com.dam.ciclismoApp.models.endpoints.UsersApi
import com.dam.ciclismoApp.models.objects.LogIn
import com.dam.ciclismoApp.models.objects.LogInResponse
import com.dam.ciclismoApp.models.objects.User
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import java.time.OffsetDateTime

class UsersRepository {
    val usersApi: UsersApi
    init {
        usersApi = Retrofit.Builder()
            .baseUrl("http://192.168.0.17:8000/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    suspend fun logIn(logIn: LogIn): LogInResponse = usersApi.logIn(logIn)
    suspend fun getUser(id:Int): User = usersApi.getUser(id)
    suspend fun updUser(user:User): Boolean = usersApi.updUser(user)
    suspend fun delUser(user:User): Boolean = usersApi.delUser(user)
}