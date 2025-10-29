package com.jasp.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Musica::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun musicaDao(): MusicaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "musica_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
