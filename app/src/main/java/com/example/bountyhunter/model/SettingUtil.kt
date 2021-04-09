package com.example.bountyhunter.model

import android.content.Context
import com.google.gson.Gson

/**
 *@author zhangzhe
 *@date 2021/4/4
 *@description 设置的自动获取和保存
 */

data class SettingData(
    var schoolId: Int = 0,
    var schoolName: String = "",
    var userSchoolName: String = "",
    var userSchoolId: Int = 0,
)

fun SettingData.toJson(): String = Gson().toJson(this)

object SettingUtil {
    private val sp =
        MainApplication.context.getSharedPreferences("BHData", Context.MODE_PRIVATE)
    var settingData: SettingData? = null
        get() {
            if (field != null) {
                return field
            }
            try {
                settingData =
                    Gson().fromJson(sp.getString("setting_data", ""), SettingData::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return field ?: SettingData()
        }

    fun save() {
        settingData?.let {
            val editor = sp.edit()
            editor.putString("setting_data", it.toJson())
            editor.commit()
        }
    }

    fun reset() {
        settingData?.let {
            val editor = sp.edit()
            editor.putString("setting_data", "")
            editor.commit()
            settingData = null
        }
    }

}