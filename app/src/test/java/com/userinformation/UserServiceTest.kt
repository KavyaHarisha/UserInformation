package com.userinformation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.userinformation.data.remote.UserService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsNull
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class UserServiceTest{

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: UserService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getPostsTest() = runBlocking {
        enqueueResponse()
        val users = service.getAllUsers().body()

        assertThat(users, IsNull.notNullValue())
        assertThat(users!!.rows.size, CoreMatchers.`is`(2))
        assertThat(users.rows[0].title, CoreMatchers.`is`("Beavers"))
    }

    private fun enqueueResponse() {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/facts.json")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

}