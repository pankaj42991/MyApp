package com.accord.myapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class EmployeeEntity(
    @PrimaryKey
    val id: String,              // Firebase UID / UUID
    val name: String,
    val role: String,            // ADMIN / EMPLOYEE
    val fixedMorning: Boolean = false,
    val active: Boolean = true
)