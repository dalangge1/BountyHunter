package com.example.bountyhunter.view.activity

import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.example.bountyhunter.R
import com.example.bountyhunter.base.BaseViewModelActivity
import com.example.bountyhunter.extensions.toast
import com.example.bountyhunter.model.Config
import com.example.bountyhunter.view.viewmodel.LoginViewModel
import com.example.moneycounter4.widgets.ProgressDialogW
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : BaseViewModelActivity<LoginViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.enterTransition = Slide(Gravity.LEFT)


        floatLoginLogin.setOnClickListener {
            ProgressDialogW.show(this, "提示", "正在登录中", false)
            Config.userId = edittextLoginUserId.text.toString()
            Config.password = edittextLoginPassword.text.toString()
            viewModel.checkLogin(this, {
                ProgressDialogW.hide()
                toast("登录成功！欢迎回来！")
                this@ActivityLogin.finish()
                val intent = Intent(this@ActivityLogin, ActivityMain::class.java).apply {
                    putExtra("login", true)
                }
                startActivity(intent)
            }, {
                ProgressDialogW.hide()
                toast("登录失败，请检查用户名或密码")
            })

        }


        tvRegister.setOnClickListener {
            val intent = Intent(this, ActivityRegister::class.java)
            window.exitTransition = Slide(Gravity.LEFT)
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair(edittextLoginUserId, "et1"),
                Pair(edittextLoginPassword, "et2"),
                Pair(floatLoginLogin, "ok"),
                Pair(logo, "logo")
            )
            startActivity(intent, option.toBundle())
        }
    }
}