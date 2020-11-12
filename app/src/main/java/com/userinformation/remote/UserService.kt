package com.userinformation.remote

import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("facts.json")
    suspend fun getAllUsers(): Response<UserService>
}