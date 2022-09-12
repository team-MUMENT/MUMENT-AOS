package com.mument_android.app.data.mapper.locker

import com.mument_android.app.data.dto.locker.LockerMyMumentDto
import com.startup.core.base.BaseMapper
import com.mument_android.app.domain.entity.locker.LockerMumentEntity

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