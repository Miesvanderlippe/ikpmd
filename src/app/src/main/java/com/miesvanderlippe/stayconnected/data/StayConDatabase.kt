package com.miesvanderlippe.stayconnected.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miesvanderlippe.stayconnected.entities.EventEntitiy


// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(EventEntitiy::class), version = 1, exportSchema = false)
public abstract class StayConDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: StayConDatabase? = null

        fun getDatabase(context: Context): StayConDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StayConDatabase::class.java,
                    "staycon_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}