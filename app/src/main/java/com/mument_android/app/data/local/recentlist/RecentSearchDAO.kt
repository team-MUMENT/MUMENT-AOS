package com.mument_android.app.data.local.recentlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import com.mument_android.app.domain.entity.home.recentlist.RecentSearchData

@Dao
interface RecentSearchDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(data: RecentSearchData)

    @Delete
    suspend fun deleteRecentSearch(data: RecentSearchData)

    @Update
    suspend fun updateRecentSearch(data: RecentSearchData)

    @Query("delete from recent_table")
    suspend fun deleteAllRecentSearchList()

    @Query("select * from recent_table order by createAt desc")
    suspend fun getAllRecentList(): List<RecentSearchData>
}