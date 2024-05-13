package com.example.demoapp.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.demoapp.data.models.User
import com.example.demoapp.domain.repository.UserRepository

class UserPagingSource constructor(
    private val userRepository: UserRepository
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val users = userRepository.getUsers(page)
            LoadResult.Page(
                data = users,
                prevKey = /*if (users.isNotEmpty()) page.minus(1) else*/ null,
                nextKey = if (users.isNotEmpty()) page.plus(1) else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}