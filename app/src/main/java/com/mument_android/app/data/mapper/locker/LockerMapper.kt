package com.mument_android.app.data.mapper.locker

import com.mument_android.app.data.dto.ResponseMyMumentListDto
import com.mument_android.app.data.mapper.BaseMapper
import com.mument_android.app.domain.entity.LockerMumentEntity

class LockerMapper: BaseMapper<ResponseMyMumentListDto, List<LockerMumentEntity>> {
    override fun map(from: ResponseMyMumentListDto): List<LockerMumentEntity> {
        val myMumentList = mutableListOf<LockerMumentEntity>()
        /*from.muments.groupBy { it.year + it.month }.onEach {
            myMumentList.add(
                LockerMumentEntity(
                    it.key,
                    it.value.map {
                        LockerMumentEntity.MumentLockerCard(
                            it._id,
                            it.content,
                            it.music,
                            it.user,
                            it.likeCount,
                            it.isPrivate,
                            it.isFirst,
                            it.impression,
                            it.feeling,
                            it.createdAt
                        )
                    }
                )
            )
        }*/
        return myMumentList
    }
}