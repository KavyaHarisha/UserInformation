package com.userinformation.data.remote

import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val userService: UserService){
    suspend fun getUsers() = userService.getAllUsers()
}