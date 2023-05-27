package com.mument_android.data.mapper.detail

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.detail.RequestReportDto
import com.mument_android.domain.entity.detail.ReportRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportMumentMapper @Inject constructor() : BaseMapper<ReportRequest, RequestReportDto> {
    override fun map(from: ReportRequest): RequestReportDto = RequestReportDto(
        from.etcContent,
        from.reportCategory
    )
}