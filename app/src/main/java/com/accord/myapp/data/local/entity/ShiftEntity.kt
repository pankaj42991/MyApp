package com.accord.myapp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "shifts",
    indices = [
        Index("date"),
        Index("employeeId")
    ]
)
data class ShiftEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val date: LocalDate,

    val shiftType: String,
    /*
      MORNING
      GENERAL
      MID
      SECOND
      NIGHT
      HOLIDAY_DAY
      HOLIDAY_NIGHT
    */

    val startTime: String,
    val endTime: String,

    val employeeId: String,

    val isHoliday: Boolean = false
)