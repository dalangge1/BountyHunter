package com.example.bountyhunter.bean.beannew

import com.google.gson.annotations.SerializedName

data class PrivateMessageItem(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("user_id")
    val userId: String = "",
    @SerializedName("friend_id")
    val friendId: String = "",
    @SerializedName("sender_id")
    val senderId: String = "",
    @SerializedName("receiver_id")
    val receiverId: String = "",
    @SerializedName("message_type")
    val messageType: Int = 0,
    @SerializedName("message_content")
    val messageContent: String = "",
    @SerializedName("send_time")
    val sendTime: Long = 0L,
    @SerializedName("status")
    val status: Int = 0,

    // 对方的个人信息
    @SerializedName("friend_user")
    var friendUser: User? = null
)
