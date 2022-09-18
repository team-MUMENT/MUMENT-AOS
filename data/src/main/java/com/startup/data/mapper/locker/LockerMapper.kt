package com.startup.data.mapper.locker

import com.startup.data.dto.locker.LockerMyMumentDto
import com.startup.domain.entity.locker.LockerMumentEntity
import com.startup.core.base.BaseMapper
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