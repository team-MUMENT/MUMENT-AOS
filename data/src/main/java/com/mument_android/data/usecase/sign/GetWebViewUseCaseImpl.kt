package com.mument_android.data.usecase.sign

import com.mument_android.domain.entity.sign.WebViewEntity
import com.mument_android.domain.repository.sign.SignRepository
import com.mument_android.domain.usecase.sign.GetWebViewUseCase
import javax.inject.Inject

class GetWebViewUseCaseImpl @Inject constructor(
    private val signRepository: SignRepository
) : GetWebViewUseCase {

    override suspend fun getWebView(page: String, os: String): WebViewEntity? {
        return signRepository.getViewView(page, os)
    }
}

