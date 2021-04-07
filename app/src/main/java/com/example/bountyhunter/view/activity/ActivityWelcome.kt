package com.example.bountyhunter.view.activity

import android.content.Intent
import android.os.Bundle
import com.example.bountyhunter.R
import com.example.bountyhunter.base.BaseViewModelActivity
import com.example.bountyhunter.extensions.toast
import com.example.bountyhunter.model.Config
import com.example.bountyhunter.view.viewmodel.LoginViewModel

class ActivityWelcome : BaseViewModelActivity<LoginViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        val p = viewModel.getUserInfo()
        Config.userId = p.first
        Config.password = p.second


        Thread {
            Thread.sleep(1000)
            viewModel.checkLogin(this, {
                toast("登录成功！欢迎回来~")
                startMainActivity()
            }, {
                startLoginActivity()
            })
        }.start()


    }

    private fun startMainActivity() {
        this@ActivityWelcome.finish()
        val mainIntent = Intent(this@ActivityWelcome, ActivityMain::class.java)
        startActivity(mainIntent)
    }

    private fun startLoginActivity() {
        this@ActivityWelcome.finish()
        val mainIntent = Intent(this@ActivityWelcome, ActivityLogin::class.java)
        startActivity(mainIntent)

    }
}