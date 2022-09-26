package com.mument_android.data.mapper.locker

import com.mument_android.data.dto.locker.LockerMyMumentDto
import com.mument_android.domain.entity.locker.LockerMumentEntity
import com.mument_android.core.base.BaseMapper
import javax.inject.Inject

class LockerMapper @Inject constructor(
    private val mumentLockerCardMapper: MumentLockerCardMapper
): BaseMapper<LockerMyMumentDto, List<LockerMumentEntity>> {
    override fun map(from: LockerMyMumentDto): List<LockerMumentEntity> {
        val lockerMumentList = mutableListOf<LockerMumentEntity>()
        val mumentsMap = from.muments.groupBy { "${it.year}년 ${it.month}월" }
        mumentsMap.entries.forEach {
            lockerMumentList.add(
                LockerMumentEntity(
                it.key,
                it.value.map { mumentLockerCardMapper.map(it) }
                )
            )
        }

        return lockerMumentList
    }
}