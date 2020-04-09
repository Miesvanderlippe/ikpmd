package com.miesvanderlippe.stayconnected.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miesvanderlippe.stayconnected.entities.EventEntitiy


// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(EventEntitiy::class), version = 1, exportSchema = false)
public abstract class StayConDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        private val LOGKEY = "StayConDatabase"
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: StayConDatabase? = null

        fun getDatabase(context: Context): StayConDatabase {
            Log.d(this.LOGKEY, "Getting Database instance...")
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                Log.d(this.LOGKEY, "Returning existing instance!")
                return tempInstance
            }
            synchronized(this) {
                Log.d(this.LOGKEY, "Creating new instance.")
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