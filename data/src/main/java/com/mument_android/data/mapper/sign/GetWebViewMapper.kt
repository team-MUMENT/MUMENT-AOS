package com.mument_android.data.mapper.sign

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.sign.WebViewDto
import com.mument_android.domain.entity.sign.WebViewEntity

class GetWebViewMapper : BaseMapper<WebViewDto?, WebViewEntity> {
    override fun map(from: WebViewDto?): WebViewEntity = WebViewEntity(
        privacy = from?.privacy,
        tos = from?.tos,
        faq = from?.faq,
        contact = from?.contact,
        appInfo = from?.appInfo,
        introduction = from?.introduction,
        license = from?.license,
        version = from?.version
    )
}
