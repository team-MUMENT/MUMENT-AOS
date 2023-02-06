package com.mument_android.domain.usecase.sign

import com.mument_android.domain.entity.sign.WebViewEntity
import com.mument_android.domain.repository.sign.SignRepository
import javax.inject.Inject

class GetWebViewUseCaseImpl @Inject constructor(
    private val signRepository : SignRepository
): GetWebViewUseCase {

    override suspend fun getWebView(page: String): WebViewEntity? {
        return signRepository.getViewView(page)
    }
}

