package com.mument_android.data.mapper.notify

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.home.NotifyDto
import com.mument_android.domain.entity.home.NotifyEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotifyMapper @Inject constructor() : BaseMapper<NotifyDto, List<NotifyEntity>> {
    override fun map(from: NotifyDto): List<NotifyEntity> =
        from.data.map { element ->
            NotifyEntity(
                element.id,
                element.type,
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