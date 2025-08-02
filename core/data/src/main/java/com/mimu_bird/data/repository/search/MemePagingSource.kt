package com.mimu_bird.data.repository.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mimu_bird.network.model.response.meme.MemeResponse
import com.mimu_bird.network.model.response.search.MemeSearchResponse

/**
 * 밈 검색 요청 Paging Source
 */
class MemePagingSource (
    private val query: String,
    private val getMemes: suspend (next: Int, query: String, limit: Int) -> MemeSearchResponse
): PagingSource<Int, MemeResponse> (){
    override fun getRefreshKey(state: PagingState<Int, MemeResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MemeResponse> {
        return try {
            val page = params.key ?: INIT_KEY
            val response = getMemes(page, query, LIMIT_PER_PAGE)

            val pageInfo = response.page

            LoadResult.Page(
                data = response.memes,
                prevKey = if (page == INIT_KEY) null else page,
                nextKey = if (pageInfo.hasMore) pageInfo.next else null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val INIT_KEY = 1
        const val LIMIT_PER_PAGE = 10
    }
}