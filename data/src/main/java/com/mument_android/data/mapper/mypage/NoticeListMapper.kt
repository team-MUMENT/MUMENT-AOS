package com.mument_android.data.mapper.mypage

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.mypage.NoticeListDto
import com.mument_android.domain.entity.mypage.NoticeListEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoticeListMapper @Inject constructor() : BaseMapper<List<NoticeListDto>, List<NoticeListEntity>> {
    override fun map(from: List<NoticeListDto>): List<NoticeListEntity> =
        from.map { element ->
            NoticeListEntity(
                element.id,
                element.title,
                element.content,
                element.createAt
            )
        }

    fun mapDetail(from: NoticeListDto): NoticeListEntity = NoticeListEntity(
        from.id,
        from.title,
        from.content,
        from.createAt
    )
}

