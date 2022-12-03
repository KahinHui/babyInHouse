package com.kahin.petinthehouse.persistence.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: String,
    val email: String,
    val password: String,
    @ColumnInfo(name = "user_name") val userName: String
)