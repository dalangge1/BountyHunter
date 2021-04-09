package com.example.bountyhunter.bean.beannew

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")//
    val userId: String = "",
    @SerializedName("stu_id")
    val stuId: String = "",
    @SerializedName("school_id")
    var schoolId: Int = 0,
    @SerializedName("nickname")//
    var nickname: String = "",
    @SerializedName("password")//
    var password: String = "",
    @SerializedName("register_date")//
    val registerDate: Long = 0L,
    @SerializedName("sex")//
    var sex: String = "",
    @SerializedName("text")//
    var text: String = "",
    @SerializedName("avatar_url")//
    var avatarUrl: String = "",
    @SerializedName("school")//
    var school: School? = null,

    )
