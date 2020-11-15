package com.userinformation.data.remote

import com.userinformation.data.entities.UserList
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("facts.json")
    suspend fun getAllUsers(): Response<UserList>
}