package com.mument_android.data.datasource.sign

import com.mument_android.data.dto.sign.SetProfileDto
import com.mument_android.data.util.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response


interface SignDataSource {
    suspend fun signDupCheck(profileId: String) : Response<Any?>

    suspend fun signPutProfile(
        image : MultipartBody.Part?,
        body: HashMap<String, RequestBody>
    ) : BaseResponse<SetProfileDto>
}