package com.example.bountyhunter.view.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.navi.AMapNavi
import com.example.bountyhunter.R
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_register.setOnClickListener {
            finish()
        }
        btn_login.setOnClickListener {
            val a = AMapNavi.getInstance(this)
            a.setUseInnerVoice(true, true)
            Log.e("sandyzhang", a.playTTS("今天天气是多云转晴，15-23度", true).toString())
        }
    }
}