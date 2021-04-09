package com.example.bountyhunter.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.bountyhunter.base.BaseViewModel
import com.example.bountyhunter.bean.beannew.PrivateMessageItem
import com.example.bountyhunter.network.*
import com.example.bountyhunter.widgets.SingleLiveEvent

/**
 *@author zhangzhe
 *@date 2021/4/8
 *@description
 */

class MessageViewModel : BaseViewModel() {

    val messageList = MutableLiveData<List<PrivateMessageItem>>()

    val deleteStatus = SingleLiveEvent<Boolean>()

    val privateMessageCount = MutableLiveData(0)


    fun getPrivateMessageList() {
        ApiGenerator.getApiService(Api::class.java).getPrivateMessageList()
            .setSchedulers()
            .checkApiError()
            .doOnError {
                toastEvent.value = "网络连接失败！"
            }
            .safeSubscribeBy {
                messageList.value = it
            }
    }

    fun countPrivateMessage() {
        ApiGenerator.getApiService(Api::class.java).countPrivateMessage()
            .setSchedulers()
            .checkApiError()
            .doOnError {
                toastEvent.value = "网络连接失败！"
            }
            .safeSubscribeBy {
                privateMessageCount.postValue(it)
            }
    }


    fun deletePrivateMessageFriend(friendId: String) {
        ApiGenerator.getApiService(Api::class.java).deletePrivateMessageFriend(friendId)
            .setSchedulers()
            .checkError()
            .doOnError {
                toastEvent.value = "网络连接失败！"
            }
            .safeSubscribeBy {
                deleteStatus.value = true
            }
    }

}