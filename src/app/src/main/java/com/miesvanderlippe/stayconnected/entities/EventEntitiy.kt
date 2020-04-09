package com.miesvanderlippe.stayconnected.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "entity_cache")
class EventEntitiy (
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "activity_name") val activityName: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "date_time") val dateTime: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "participation") val participates: Boolean
)