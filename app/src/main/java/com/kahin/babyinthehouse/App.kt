package com.kahin.babyinthehouse

import android.app.Application
import androidx.room.Room
import com.kahin.babyinthehouse.persistence.db.AppDatabase

class App : Application() {

    companion object {
        lateinit var appDb: AppDatabase
    }


    override fun onCreate() {
        super.onCreate()
        appDb = Room.databaseBuilder(this, AppDatabase::class.java, "BIH-DB").build()

    }
}