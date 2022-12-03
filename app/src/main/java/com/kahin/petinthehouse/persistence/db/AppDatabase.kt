package com.kahin.petinthehouse.persistence.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kahin.petinthehouse.persistence.db.dao.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}