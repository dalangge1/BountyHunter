package com.example.bountyhunter.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.bountyhunter.base.BaseViewModel
import com.example.bountyhunter.bean.beannew.User
import com.example.bountyhunter.network.*

class IndividualViewModel : BaseViewModel() {

    val user = MutableLiveData<User>()

    fun getUser(userId: String) {
        ApiGenerator.getApiService(Api::class.java).getUserInfo(userId)
            .checkApiError()
            .setSchedulers()
            .doOnError {
                toastEvent.value = "获取用户信息失败！"
            }.safeSubscribeBy {
                user.postValue(it)
            }.lifeCycle()
    }
}