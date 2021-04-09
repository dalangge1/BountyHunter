package com.example.bountyhunter.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.bountyhunter.base.BaseViewModel
import com.example.bountyhunter.bean.beannew.Good
import com.example.bountyhunter.network.*

/**
 *@author zhangzhe
 *@date 2021/4/7
 *@description
 */

class MarketViewModel : BaseViewModel() {

    var schoolId: Int = -1

    val goodList = MutableLiveData<List<Good>>()


    fun getAllGood() {
        ApiGenerator.getApiService(Api::class.java).getAllGood(schoolId)
            .setSchedulers()
            .checkApiError()
            .doOnError {
                toastEvent.value = "网络连接失败！"
            }
            .safeSubscribeBy {
                goodList.value = it
            }
    }


}