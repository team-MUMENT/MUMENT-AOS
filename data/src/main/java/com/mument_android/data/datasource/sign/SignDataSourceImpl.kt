package com.mument_android.data.datasource.sign

import com.mument_android.data.dto.sign.SetProfileDto
import com.mument_android.data.network.sign.SignApiService
import com.mument_android.data.util.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class SignDataSourceImpl @Inject constructor(
    private val signApiService: SignApiService
) : SignDataSource {
    override suspend fun signDupCheck(profileId: String): Response<Any?> {
        return signApiService.signDuplicationCheck(profileId)
    }

    override suspend fun signPutProfile(
        image: MultipartBody.Part?,
        body: HashMap<String, RequestBody>
    ): BaseResponse<SetProfileDto> {
        return signApiService.putProfile(image,body)
    }

}