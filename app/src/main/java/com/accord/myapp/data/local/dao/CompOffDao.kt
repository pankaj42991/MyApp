package com.accord.myapp.data.local.dao

import androidx.room.*
import com.accord.myapp.data.local.entity.CompOffEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompOffDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(compOff: CompOffEntity)

    @Query("""
        SELECT * FROM comp_off 
        WHERE employeeId = :employeeId
        AND status = 'EARNED'
        ORDER BY earnedDate ASC
    """)
    suspend fun getAvailableCompOff(employeeId: String): List<CompOffEntity>

    @Query("""
        SELECT * FROM comp_off
        WHERE employeeId = :employeeId
    """)
    fun getAllCompOff(employeeId: String): Flow<List<CompOffEntity>>

    @Update
    suspend fun update(compOff: CompOffEntity)
}