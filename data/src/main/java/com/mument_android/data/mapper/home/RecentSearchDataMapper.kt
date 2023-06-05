package com.mument_android.data.mapper.home

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.local.recentlist.RecentSearchDataEntity
import com.mument_android.domain.entity.home.RecentSearchData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentSearchDataMapper @Inject constructor() : BaseMapper<List<RecentSearchDataEntity>, List<RecentSearchData>> {
    override fun map(from: List<RecentSearchDataEntity>): List<RecentSearchData> =
        from.map { element ->
            RecentSearchData(
                element._id,
                element.artist,
                element.image,
                element.name,
                element.createAt,
            )
        }

    fun mapReverse(from: RecentSearchData): RecentSearchDataEntity =
        RecentSearchDataEntity(from._id, from.artist, from.image, from.name, from.createAt)

}