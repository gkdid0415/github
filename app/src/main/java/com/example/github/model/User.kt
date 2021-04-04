package com.example.github.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("total_count") val count: Int,
    @SerializedName("items") val items: List<UserInfo>
)

data class UserInfo(
    @SerializedName("login") val login: String, // 사용자 이름
    @SerializedName("avatar_url") val avatarUrl: String // 프로필 이미지 URL
)

@Entity(tableName = "favorite")
data class FavoriteInfo(
    @PrimaryKey val login: String, // 사용자 이름
    val avatarUrl: String // 프로필 이미지 URL
)