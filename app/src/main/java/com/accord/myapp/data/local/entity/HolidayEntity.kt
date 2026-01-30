package com.accord.myapp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "holidays",
    indices = [
        Index(value = ["date"], unique = true)
    ]
)
data class HolidayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val date: LocalDate,

    val name: String,          // Diwali, Holi, Company Holiday

    val repeatYearly: Boolean = false
)