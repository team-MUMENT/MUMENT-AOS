package com.mument_android.data.local.todaymument

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Query
import com.mument_android.domain.entity.home.TodayMument

@Dao
interface TodayMumentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDayMument(data: TodayMumentEntity)

    @Delete
    suspend fun deleteTodayMument(data: TodayMumentEntity)

    @Update
    suspend fun updateTodayMument(data: TodayMumentEntity)

    @Query("SELECT * FROM today_mument_table")
    suspend fun getTodayMument(): TodayMumentEntity?

    @Query("delete from today_mument_table")
    suspend fun dropTodayMument():Int
}