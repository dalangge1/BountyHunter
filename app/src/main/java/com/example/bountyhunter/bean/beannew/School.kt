package com.example.bountyhunter.bean.beannew

import com.google.gson.annotations.SerializedName

data class School(
    @SerializedName("school_id")
    val schoolId: Int = 0,
    @SerializedName("school_name")
    val schoolName: String = "",
    @SerializedName("statement")
    val statement: String = "",
    @SerializedName("school_avatar_url")
    val schoolAvatarUrl: String = "",
    @SerializedName("school_member")
    val schoolMember: Int = 0
)
