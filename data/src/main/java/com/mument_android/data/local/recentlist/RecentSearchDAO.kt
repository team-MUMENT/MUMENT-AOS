package com.mument_android.data.local.recentlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Query
import com.mument_android.domain.entity.home.RecentSearchData

@Dao
interface RecentSearchDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(data: RecentSearchDataEntity)

    @Delete
    suspend fun deleteRecentSearch(data: RecentSearchDataEntity)

    @Update
    suspend fun updateRecentSearch(data: RecentSearchDataEntity)

    @Query("delete from recent_table")
    suspend fun deleteAllRecentSearchList()

    @Query("select * from recent_table order by createAt desc")
    suspend fun getAllRecentList(): List<RecentSearchDataEntity>
}