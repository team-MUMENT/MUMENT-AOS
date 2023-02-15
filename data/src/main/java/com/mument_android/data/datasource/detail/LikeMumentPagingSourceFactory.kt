package com.mument_android.data.datasource.detail

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.data.network.detail.HistoryService
import com.mument_android.data.util.ResultWrapper
import com.mument_android.data.util.catchingApiCall
import com.mument_android.domain.entity.history.HistoryRequestParams
import com.mument_android.domain.entity.history.MumentHistory
import com.mument_android.domain.entity.user.UserEntity

class LikeMumentPagingSourceFactory(
    private val detailApiService: DetailApiService,
    private val mumentId: String
) : PagingSource<Int, UserEntity>() {
    override fun getRefreshKey(state: PagingState<Int, UserEntity>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> {
        val key = params.key ?: Like_STARTING_PAGE_INDEX

        val response = catchingApiCall {
            detailApiService.fetchUsersWhoLikeMument(
                mumentId,
                params.loadSize,
                (key - 1) * (params.loadSize)
            )
        }.let { result ->
            Log.e("Result", result.toString())
            when (result) {
                is ResultWrapper.Success -> {
                    Log.e("Result Success", result.data.toString())
                    result.data?.data?.map { it.toUserEntity() } ?: listOf()
                }
                is ResultWrapper.GenericError -> {
                    Log.e("Result Fail", result.message.toString())
                    listOf()
                }
                ResultWrapper.NetworkError -> {
                    Log.e("Result Fail", "newwork")
                    listOf()
                }
                else -> {
                    listOf()
                }
            }
        }
        Log.e("RESPONSE", response.toString())
        return try {
            LoadResult.Page(
                data = response,
                nextKey = if (response.isEmpty()) null else key + (params.loadSize / 20),
                prevKey = if (key == Like_STARTING_PAGE_INDEX) null else key - 1
            )
        } catch (e: IllegalArgumentException) {   //LoadResult.Page 객체 생성시에 require 부분에서 해당 exception throw 하기 때문에 처리
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val Like_STARTING_PAGE_INDEX = 1
    }
}