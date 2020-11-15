package com.userinformation.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.userinformation.data.dao.AppDatabase
import com.userinformation.data.remote.UserRemoteDataSource
import com.userinformation.data.remote.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit =  Retrofit.Builder()
        .baseUrl("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = AppDatabase.getDatabase(application)

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(userService: UserService) = UserRemoteDataSource(userService)

}