package com.miesvanderlippe.stayconnected.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miesvanderlippe.stayconnected.entities.EventEntitiy


@Dao
interface EventDao {

    @Query("SELECT * from entity_cache ORDER BY date_time ASC")
    fun getAllEvents(): LiveData<List<EventEntitiy>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: EventEntitiy)

    @Query("DELETE FROM entity_cache")
    suspend fun deleteAll()
}