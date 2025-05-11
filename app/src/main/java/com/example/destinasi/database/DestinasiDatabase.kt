package com.example.destinasi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.destinasi.model.Destinasi

@Database(entities = [Destinasi::class], version = 2, exportSchema = false)
abstract class DestinasiDatabase : RoomDatabase() {
    abstract val destinasiDao: DestinasiDao

    companion object {
        @Volatile
        private var INSTANCE: DestinasiDatabase? = null

        fun getInstance(context: Context): DestinasiDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DestinasiDatabase::class.java,
                        "destinasi_database"
                    )
                        .fallbackToDestructiveMigration(false)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}