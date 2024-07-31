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

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {
    fun getUsersStream() = Pager(
        PagingConfig(pageSize = 20)
    ){
        UserPagingSource(apiService, userDao)
    }.flow

    suspend fun getUserDetail(login: String): User{
        return apiService.getUserDetail(login = login)
    }

    suspend fun getLocalUsers(): List<User>{
        return userDao.getAllUsers().map { it.toUser() }
    }

    suspend fun saveUsers(users: List<User>){
        userDao.insertAll(users.map { it.toUserEntity() })
    }

    suspend fun clearLocalUsers(){
        userDao.deleteAllUsers()
    }
}