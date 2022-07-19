package com.mument_android.app.data.local.recentlist

import androidx.room.*


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