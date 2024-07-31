package com.nhinhnguyenuit.jetpackproject.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("location")
    val location: String?,
    val followers: Int,
    val following: Int,
)
