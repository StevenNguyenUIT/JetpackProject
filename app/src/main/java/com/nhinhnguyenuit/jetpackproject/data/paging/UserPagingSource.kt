package com.nhinhnguyenuit.jetpackproject.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nhinhnguyenuit.jetpackproject.data.local.UserDao
import com.nhinhnguyenuit.jetpackproject.data.local.toUser
import com.nhinhnguyenuit.jetpackproject.data.local.toUserEntity
import com.nhinhnguyenuit.jetpackproject.data.model.User
import com.nhinhnguyenuit.jetpackproject.data.network.ApiService
import javax.inject.Inject


internal const val PAGE_SIZE = 20

class UserPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 0
            val response = apiService.getUsers(perPage = PAGE_SIZE, page)
            userDao.insertAll(response.map { it.toUserEntity() })
            val nextKey = if (response.isEmpty()) null else response.last().id

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            val localData = userDao.getAllUsers().map { it.toUser() }
            LoadResult.Page(
                data = localData,
                prevKey = null,
                nextKey = localData.lastOrNull()?.id
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}