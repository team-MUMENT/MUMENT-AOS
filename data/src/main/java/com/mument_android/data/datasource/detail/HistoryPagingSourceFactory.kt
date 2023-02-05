package com.mument_android.data.datasource.detail

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mument_android.data.network.detail.HistoryService
import com.mument_android.data.util.ResultWrapper
import com.mument_android.data.util.catchingApiCall
import com.mument_android.domain.entity.history.HistoryRequestParams
import com.mument_android.domain.entity.history.MumentHistory

class HistoryPagingSourceFactory(
    private val historyService: HistoryService,
    private val historyRequestParams: HistoryRequestParams
) : PagingSource<Int, MumentHistory>() {
    override fun getRefreshKey(state: PagingState<Int, MumentHistory>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MumentHistory> {
        val key = params.key ?: HiSTORY_STARTING_PAGE_INDEX
        val response = catchingApiCall {
            historyService.getMumentHistory(
                historyRequestParams.userId,
                historyRequestParams.musicId,
                historyRequestParams.default,
                params.loadSize,
                (key - 1) * (params.loadSize)
            )
        }.let { result ->
            Log.e("LIStADAPTER FACTORY", result.toString())
            when (result) {
                is ResultWrapper.Success -> {
                    result.data?.data?.mumentHistory ?: listOf()
                }
                else -> {
                    listOf()
                }
            }
        }
        return try {
            LoadResult.Page(
                data = response,
                nextKey = if (response.isEmpty()) null else key + (params.loadSize / 10),
                prevKey = if (key == HiSTORY_STARTING_PAGE_INDEX) null else key - 1
            )
        } catch (e: IllegalArgumentException) {   //LoadResult.Page 객체 생성시에 require 부분에서 해당 exception throw 하기 때문에 처리
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val HiSTORY_STARTING_PAGE_INDEX = 1
    }
}