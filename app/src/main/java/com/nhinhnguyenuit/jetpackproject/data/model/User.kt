package com.nhinhnguyenuit.jetpackproject.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    private val name: String,
    @SerializedName("avatar_url")
    private val avatarUrl: String,
    @SerializedName("html_url")
    private val htmlUrl: String,
    @SerializedName("location")
    private val location: String = "Viet Nam",
    @SerializedName("followers_url")
    private val followers: String,
    @SerializedName("following_url")
    private val following: String,
)
