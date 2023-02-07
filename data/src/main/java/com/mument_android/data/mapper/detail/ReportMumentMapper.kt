package com.mument_android.data.mapper.detail

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.detail.RequestReportDto
import com.mument_android.data.dto.detail.ResponseBlockUserDto
import com.mument_android.domain.entity.detail.ReportRequest

class ReportMumentMapper: BaseMapper<ReportRequest, RequestReportDto> {
    override fun map(from: ReportRequest): RequestReportDto  = RequestReportDto(
        from.etcContent,
        from.reportCategory
    )
}