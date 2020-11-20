package com.userinformation.data.repository

import com.userinformation.data.dao.UserDao
import com.userinformation.data.entities.User
import com.userinformation.data.entities.UserList
import com.userinformation.data.remote.UserRemoteDataSource
import com.userinformation.utils.NetworkDataRepository
import com.userinformation.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class UserRepository @Inject constructor(
            private val remoteDataSource: UserRemoteDataSource,
            private val localDataSource: UserDao) {

    fun getAllUser(): Flow<State<List<User>>> {
        return object : NetworkDataRepository<List<User>, UserList>() {

            override suspend fun saveRemoteData(response: UserList) {
                localDataSource.deleteAllUsers()
                localDataSource.insertAll(response.rows)
            }

            override fun fetchFromLocal(): Flow<List<User>> = localDataSource.getAllUsers()

            override suspend fun fetchFromRemote(): Response<UserList> = remoteDataSource.getUsers()
        }.asFlow()
    }
}