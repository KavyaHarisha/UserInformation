package com.userinformation.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.userinformation.data.entities.User.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String?,
    val description: String?,
    val imageHref: String?
){
    companion object {
        const val TABLE_NAME = "user_table"
    }
}