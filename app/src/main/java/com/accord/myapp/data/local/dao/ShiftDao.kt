package com.accord.myapp.data.local.dao

import androidx.room.*
import com.accord.myapp.data.local.entity.ShiftEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface ShiftDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShift(shift: ShiftEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shifts: List<ShiftEntity>)

    @Query("""
        SELECT * FROM shifts 
        WHERE date BETWEEN :start AND :end
        ORDER BY date ASC
    """)
    fun getShiftsBetween(
        start: LocalDate,
        end: LocalDate
    ): Flow<List<ShiftEntity>>

    @Query("""
        SELECT * FROM shifts 
        WHERE date = :date
    """)
    suspend fun getShiftsByDate(date: LocalDate): List<ShiftEntity>

    @Query("""
        SELECT COUNT(*) FROM shifts
        WHERE employeeId = :employeeId
        AND shiftType = 'NIGHT'
    """)
    suspend fun getNightShiftCount(employeeId: String): Int

    @Query("""
        DELETE FROM shifts 
        WHERE date = :date
    """)
    suspend fun deleteShiftsByDate(date: LocalDate)
}