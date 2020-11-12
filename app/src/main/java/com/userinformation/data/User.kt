package com.userinformation.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val imageHref: String
)