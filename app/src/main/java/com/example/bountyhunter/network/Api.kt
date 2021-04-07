package com.example.bountyhunter.network

import com.example.bountyhunter.bean.base.ApiWrapper
import com.example.bountyhunter.bean.beannew.DynamicItem
import com.example.bountyhunter.bean.beannew.Good
import com.example.bountyhunter.bean.beannew.PrivateMessageItem
import com.example.bountyhunter.bean.beannew.User
import com.example.moneycounter4.beannew.InfoWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *@author zhangzhe
 *@date 2021/3/3
 *@description
 */

interface Api {

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=register")
    fun register(
        @Field("user_id") userId: String,
        @Field("password") password: String
    ): Observable<InfoWrapper>


    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=getAllDynamic")
    fun getAllDynamic(
        @Field("pos") pos: Int,
        @Field("size") size: Int,
        @Field("topic") topic: String
    ): Observable<ApiWrapper<List<DynamicItem>>>

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=releaseDynamic")
    fun releaseDynamic(
        @Field("text") text: String,
        @Field("topic") topic: String
    ): Observable<InfoWrapper>

    // 发布有图片的帖子，图片url用逗号分割
    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=releaseDynamic")
    fun releaseDynamic(
        @Field("text") text: String,
        @Field("topic") topic: String,
        @Field("pic_url") picUrl: String
    ): Observable<InfoWrapper>


    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=reply")
    fun reply(
        @Field("text") text: String,
        @Field("reply_id") replyId: Int,
        @Field("which") which: Int
    ): Observable<InfoWrapper>


    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=getUserInfo")
    fun getUserInfo(
        @Field("user_id") userId: String
    ): Observable<ApiWrapper<User>>

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=reversePraise")
    fun reversePraise(
        @Field("id") id: Int,
        @Field("which") which: Int
    ): Observable<InfoWrapper>

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=deleteComment")
    fun deleteComment(
        @Field("id") id: Int,
        @Field("which") which: Int
    ): Observable<InfoWrapper>

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=deleteDynamic")
    fun deleteDynamic(
        @Field("dynamic_id") dynamicId: Int
    ): Observable<InfoWrapper>


    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=changeUserInfo")
    fun changeUserInfo(
        @Field("user_info") userInfo: String
    ): Observable<InfoWrapper>

    @FormUrlEncoded
    @POST("BountyHunterTransactions?action=getAllMember")
    fun getAllMember(@Field("school_id") schoolId: Int): Observable<ApiWrapper<List<User>>>

    @FormUrlEncoded
    @POST("BountyHunterTransactions?action=getAllGood")
    fun getAllGood(@Field("school_id") schoolId: Int): Observable<ApiWrapper<List<Good>>>

    @FormUrlEncoded
    @POST("BountyHunterTransactions?action=releaseGood")
    fun releaseGood(
        @Field("price") price: Double,
        @Field("good_name") goodName: String,
        @Field("statement") statement: String,
        @Field("pic_url") pic_url: String
    ): Observable<InfoWrapper>

    @FormUrlEncoded
    @POST("BountyHunterTransactions?action=deleteGood")
    fun deleteGood(
        @Field("good_id") goodId: Int
    ): Observable<InfoWrapper>

    @FormUrlEncoded
    @POST("BountyHunterTransactions?action=getGood")
    fun getGood(
        @Field("good_id") goodId: Int
    ): Observable<InfoWrapper>

    /**
     * 私信
     */

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=getPrivateMessageList")
    fun getPrivateMessageList(): Observable<ApiWrapper<List<PrivateMessageItem>>>

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=getPrivateMessage")
    fun getPrivateMessage(): Observable<ApiWrapper<List<PrivateMessageItem>>>

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=submitPrivateMessage")
    fun submitPrivateMessage(
        @Field("friend_id") friendId: String,
        @Field("content") content: String,
    ): Observable<InfoWrapper>

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=deletePrivateMessage")
    fun deletePrivateMessage(
        @Field("id") id: Int
    ): Observable<InfoWrapper>

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=deletePrivateMessageFriend")
    fun deletePrivateMessageFriend(
        @Field("friend_id") friendId: String
    ): Observable<InfoWrapper>

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=countPrivateMessage")
    fun countPrivateMessage(): Observable<ApiWrapper<Int>>

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=countPrivateMessageFriend")
    fun countPrivateMessageFriend(
        @Field("friend_id") friendId: String
    ): Observable<ApiWrapper<Int>>

    @FormUrlEncoded
    @POST("BountyHunterCommunity?action=clearCountPrivateMessageFriend")
    fun clearCountPrivateMessageFriend(
        @Field("friend_id") friendId: String
    ): Observable<InfoWrapper>


}