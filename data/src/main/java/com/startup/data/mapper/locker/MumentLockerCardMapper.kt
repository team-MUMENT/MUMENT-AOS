package com.startup.data.mapper.locker

import com.startup.data.dto.locker.LockerMyMumentDto
import com.startup.domain.entity.locker.LockerMumentEntity
import com.startup.core.base.BaseMapper

class MumentLockerCardMapper:
    BaseMapper<LockerMyMumentDto.Mument, LockerMumentEntity.MumentLockerCard> {
    override fun map(from: LockerMyMumentDto.Mument): LockerMumentEntity.MumentLockerCard {
        return LockerMumentEntity.MumentLockerCard(
            from._id,
            from.cardTag,
            from.content,
            from.createdAt,
            from.feelingTag,
            from.impressionTag,
            from.music?._id,
            from.music?.image,
            from.music?.name,
            from.music?.artist,
            from.user?._id,
            from.user?.image,
            from.user?.name,
            from.likeCount,
            from.isLiked,
            from.isPrivate,
            from.isFirst,
            from.month,
            from.year
        )
    }
}