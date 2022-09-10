package com.mument_android.app.data.local.todaymument

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import com.mument_android.app.domain.entity.home.todaymument.TodayMumentEntity

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