package com.example.bountyhunter.bean.beannew

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Good(
    @SerializedName("good_id")
    val goodId: Int = 0,
    @SerializedName("good_name")
    val goodName: String = "",
    @SerializedName("statement")
    val statement: String = "",
    @SerializedName("price")
    val price: Double = 0.0,
    @SerializedName("pic_url")
    val picUrl: String = "",
    @SerializedName("submit_time")
    val submitTime: Long = 0L,
    @SerializedName("from_school_id")
    val fromSchoolId: Int = 0,
    @SerializedName("from_user_id")
    val fromUserId: String = "",
    @SerializedName("get_user_id")
    val getUserId: String = "",
    @SerializedName("submit_user")
    var submitUser: User? = null,
    @SerializedName("get_user")
    var getUser: User? = null

) : Serializable
