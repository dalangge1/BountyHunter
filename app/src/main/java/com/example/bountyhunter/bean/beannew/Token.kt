package com.example.bountyhunter.bean.beannew

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("token")
    val token: Int = 0
)