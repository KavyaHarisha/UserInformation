package com.userinformation

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.userinformation.data.dao.AppDatabase
import com.userinformation.data.entities.User
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest{
    private lateinit var mDatabase: AppDatabase

    @Before
    fun init() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insert_and_select_posts() = runBlocking {
        val posts = listOf(
            User(1, "Test 1", "Test 1", "Test 1"),
            User(2, "Test 2", "Test 2", "Test 3")
        )

        mDatabase.userDao().insertAll(posts)

        val dbPosts = mDatabase.userDao().getAllUsers().toList()[0]

        MatcherAssert.assertThat(dbPosts, CoreMatchers.equalTo(posts))
    }

    @After
    fun cleanup() {
        mDatabase.close()
    }
}