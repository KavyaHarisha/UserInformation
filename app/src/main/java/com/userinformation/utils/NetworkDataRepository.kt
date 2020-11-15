package com.userinformation.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.*
import retrofit2.Response


abstract class  NetworkDataRepository<RESULT, REQUEST> {

    fun asFlow() = flow<State<RESULT>> {

        emit(State.loading())

        emit(State.success(fetchFromLocal().first()))

        val apiResponse = fetchFromRemote()

        val remotePosts = apiResponse.body()

        if (apiResponse.isSuccessful && remotePosts != null) {
            saveRemoteData(remotePosts)
        } else {
            emit(State.error(apiResponse.message()))
        }

        emitAll(
            fetchFromLocal().map {
                State.success<RESULT>(it)
            }
        )
    }.catch { e ->
        emit(State.error("Network error! Can't get latest posts."))
        e.printStackTrace()
    }


    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)


    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>


    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<REQUEST>
}
