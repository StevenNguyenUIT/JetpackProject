package com.nhinhnguyenuit.jetpackproject.data.network

import com.nhinhnguyenuit.jetpackproject.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}