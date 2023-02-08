package com.mument_android.home.notify

import com.mument_android.core.base.BaseMapper
import com.mument_android.domain.entity.home.NotifyEntity
import com.mument_android.home.models.Notify
import com.mument_android.home.util.NotifyType

class NotifyItemMapper : BaseMapper<List<NotifyEntity>, List<Notify>> {
    override fun map(from: List<NotifyEntity>): List<Notify> = from.map { element ->
        Notify(
            element.id,
            NotifyType.valueOf(element.type.uppercase()),
            element.userId,
            element.isDeleted,
            element.isRead,
            element.createdAt,
            element.linkId,
            element.notice,
            element.like
        )
    }
}