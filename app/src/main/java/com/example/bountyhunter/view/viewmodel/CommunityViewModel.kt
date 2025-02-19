package com.example.bountyhunter.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.bountyhunter.base.BaseViewModel
import com.example.bountyhunter.bean.beannew.DynamicItem
import com.example.bountyhunter.bean.beannew.ReplyInfo
import com.example.bountyhunter.network.*
import com.example.bountyhunter.widgets.SingleLiveEvent


class CommunityViewModel : BaseViewModel() {

    val dynamicList: MutableLiveData<ArrayList<DynamicItem>> = MutableLiveData(arrayListOf())

    val releaseDynamicStatus: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val replyStatus: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val deleteStatus: SingleLiveEvent<Int> = SingleLiveEvent() // 1为删除帖子 2为删除回复

    val replyInfo: MutableLiveData<ReplyInfo> = MutableLiveData()

    var schoolId: Int = 0


    fun refreshDynamic() {
        getAllDynamic(0, 50, schoolId.toString())
    }

    fun getAllDynamic(pos: Int, size: Int, topic: String) {
        ApiGenerator.getApiService(Api::class.java).getAllDynamic(pos, size, topic)
            .checkApiError()
            .setSchedulers()
            .doOnError {
                toastEvent.value = "请求失败！"
            }.safeSubscribeBy {
                dynamicList.value = (it as ArrayList<DynamicItem>?)
            }.lifeCycle()
    }


    fun releaseDynamic(text: String, topic: String) {
        progressDialogEvent.value = "正在上传中..."
        ApiGenerator.getApiService(Api::class.java).releaseDynamic(text, topic)
            .checkError()
            .setSchedulers()
            .doOnError {
                toastEvent.value = "发送帖子失败！"
            }.doFinally {
                progressDialogEvent.value = ""
            }.safeSubscribeBy {
                releaseDynamicStatus.postValue(true)
                toastEvent.value = "发送成功！"
            }.lifeCycle()
    }

    fun releaseDynamic(text: String, topic: String, picUrl: List<String>) {
        progressDialogEvent.value = "正在上传中..."
        val s = StringBuilder()
        picUrl.forEach {
            s.append("$it,")
        }
        s.deleteCharAt(s.lastIndex)
        ApiGenerator.getApiService(Api::class.java).releaseDynamic(text, topic, s.toString())
            .checkError()
            .setSchedulers()
            .doOnError {
                toastEvent.value = "发送帖子失败！"
            }.doFinally {
                progressDialogEvent.value = ""
            }.safeSubscribeBy {
                releaseDynamicStatus.postValue(true)
                toastEvent.value = "发送成功！"
            }.lifeCycle()
    }

    /**
     * @param which 0为直接回复帖子 1为回复帖子下面的评论 2为回复二级评论
     */
    private fun reply(text: String, replyId: Int, which: Int) {
        progressDialogEvent.value = "正在上传中..."
        ApiGenerator.getApiService(Api::class.java).reply(text, replyId, which)
            .checkError()
            .setSchedulers()
            .doOnError {
                toastEvent.value = "回复失败！"
            }.doFinally {
                progressDialogEvent.value = ""
            }.safeSubscribeBy {
                toastEvent.value = "回复成功！"
                replyStatus.postValue(true)
            }.lifeCycle()
    }

    fun reply(text: String) {
        replyInfo.value?.let {
            if (it.replyId != -1 && it.which != -1 && text.isNotBlank()) {
                reply(text, it.replyId, it.which)
            } else {
                toastEvent.value = "回复参数不合法"
            }
        }
    }

    fun deleteDynamic(dynamicId: Int) {
        ApiGenerator.getApiService(Api::class.java).deleteDynamic(dynamicId)
            .checkError()
            .setSchedulers()
            .doOnError {
                toastEvent.value = "删除失败！"
            }.doFinally {
                progressDialogEvent.value = ""
            }.safeSubscribeBy {
                toastEvent.value = "删除成功！"
                deleteStatus.postValue(1)
            }.lifeCycle()
    }

    fun deleteComment(id: Int, which: Int) {
        ApiGenerator.getApiService(Api::class.java).deleteComment(id, which)
            .checkError()
            .setSchedulers()
            .doOnError {
                toastEvent.value = "删除失败！"
            }.doFinally {
                progressDialogEvent.value = ""
            }.safeSubscribeBy {
                toastEvent.value = "删除成功！"
                deleteStatus.postValue(2)
            }.lifeCycle()
    }

}