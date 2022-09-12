package com.startup.data.local.todaymument

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Query
import com.startup.domain.entity.home.TodayMumentEntity

@Dao
interface TodayMumentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDayMument(data: TodayMumentEntity)

    @Delete
    suspend fun deleteTodayMument(data: TodayMumentEntity)

    @Update
    suspend fun updateTodayMument(data: TodayMumentEntity)

    @Query("SELECT * FROM today_mument_table WHERE userId = :userId")
    suspend fun getTodayMument(userId:String): TodayMumentEntity
}