package com.nhinhnguyenuit.jetpackproject.data.network

import com.nhinhnguyenuit.jetpackproject.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("per_page") perPage: Int,
        @Query("since") since: Int
    ): List<User>

    @GET("users/{login}")
    suspend fun getUserDetail(
        @Path("login") login: String
    ): User
}