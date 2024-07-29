package com.nhinhnguyenuit.jetpackproject.data.repository

import com.nhinhnguyenuit.jetpackproject.data.model.User
import com.nhinhnguyenuit.jetpackproject.data.network.ApiService
import com.nhinhnguyenuit.jetpackproject.data.network.RetrofitInstance
import javax.inject.Inject

class UserRepository @Inject constructor(
    val apiService: ApiService
) {
    suspend fun getUsers(): List<User> = RetrofitInstance.api.getUsers()
}