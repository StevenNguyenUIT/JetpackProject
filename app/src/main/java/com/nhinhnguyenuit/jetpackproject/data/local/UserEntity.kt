package com.nhinhnguyenuit.jetpackproject.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.nhinhnguyenuit.jetpackproject.data.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val location: String?,
    val followers: Int,
    val following: Int,
)

fun UserEntity.toUser(): User {
    return User(id, name, avatarUrl, htmlUrl, location, followers, following)
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(id, name, avatarUrl, htmlUrl, location, followers, following)
}
