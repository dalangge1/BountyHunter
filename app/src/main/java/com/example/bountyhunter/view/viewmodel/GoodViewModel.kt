package com.example.bountyhunter.view.viewmodel

import com.example.bountyhunter.base.BaseViewModel
import com.example.bountyhunter.network.*
import com.example.bountyhunter.widgets.SingleLiveEvent


class GoodViewModel : BaseViewModel() {

    val releaseGoodStatus: SingleLiveEvent<Boolean> = SingleLiveEvent()


    fun releaseGood(price: Double, name: String, statement: String, picUrl: String) {
        progressDialogEvent.value = "正在上传中..."
        ApiGenerator.getApiService(Api::class.java).releaseGood(price, name, statement, picUrl)
            .checkError()
            .setSchedulers()
            .doOnError {
                toastEvent.value = "发布商品失败！"
            }.doFinally {
                progressDialogEvent.value = ""
            }.safeSubscribeBy {
                releaseGoodStatus.postValue(true)
                toastEvent.value = "发布成功！"
            }.lifeCycle()
    }
}