package com.accord.myapp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "comp_off",
    indices = [
        Index("employeeId"),
        Index("status")
    ]
)
data class CompOffEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val employeeId: String,

    val earnedDate: LocalDate,

    val source: String,
    /*
      SUNDAY
      SATURDAY_2_4
      FESTIVAL
    */

    val shiftType: String,
    /*
      DAY
      NIGHT
    */

    val usedDate: LocalDate? = null,

    val status: String
    /*
      EARNED
      USED
      CARRY_FORWARD
    */
)