package com.userinformation.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.userinformation.dao.AppDatabase
import com.userinformation.remote.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideUserService(retrofit: Retrofit): UserService =  retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()


}