package com.mument_android.app.data.local.todaymument

import androidx.room.*

@Dao
interface TodayMumentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDayMument(data: TodayMumentEntity)

    @Delete
    suspend fun deleteTodayMument(data: TodayMumentEntity)

    @Update
    suspend fun updateTodayMument(data: TodayMumentEntity)

    @Query("SELECT * FROM today_mument_table")
    suspend fun getTodayMument(): List<TodayMumentEntity>
}