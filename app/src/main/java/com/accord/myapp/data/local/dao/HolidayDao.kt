package com.accord.myapp.data.local.dao

import androidx.room.*
import com.accord.myapp.data.local.entity.HolidayEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface HolidayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoliday(holiday: HolidayEntity)

    @Query("SELECT * FROM holidays")
    fun getAllHolidays(): Flow<List<HolidayEntity>>

    @Query("SELECT * FROM holidays WHERE date = :date LIMIT 1")
    suspend fun getHolidayByDate(date: LocalDate): HolidayEntity?

    @Delete
    suspend fun deleteHoliday(holiday: HolidayEntity)
}