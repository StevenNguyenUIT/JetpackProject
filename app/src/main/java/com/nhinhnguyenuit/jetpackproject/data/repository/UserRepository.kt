package com.nhinhnguyenuit.jetpackproject.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.nhinhnguyenuit.jetpackproject.data.local.UserDao
import com.nhinhnguyenuit.jetpackproject.data.local.toUser
import com.nhinhnguyenuit.jetpackproject.data.local.toUserEntity
import com.nhinhnguyenuit.jetpackproject.data.model.User
import com.nhinhnguyenuit.jetpackproject.data.network.ApiService
import com.nhinhnguyenuit.jetpackproject.data.paging.UserPagingSource
import javax.inject.Inject
import com.nhinhnguyenuit.jetpackproject.data.paging.PAGE_SIZE

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {

    fun getUsersStream() = Pager(
        PagingConfig(pageSize = PAGE_SIZE)
    ) {
        UserPagingSource(apiService, userDao)
    }.flow

    suspend fun getUserDetail(login: String) = apiService.getUserDetail(login = login)

    suspend fun getLocalUsers() = userDao.getAllUsers().map { it.toUser() }

    suspend fun saveUsers(users: List<User>) {
        userDao.insertAll(users.map { it.toUserEntity() })
    }

    suspend fun clearLocalUsers() {
        userDao.deleteAllUsers()
    }
}