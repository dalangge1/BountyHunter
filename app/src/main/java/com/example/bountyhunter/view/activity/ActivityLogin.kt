package com.example.bountyhunter.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bountyhunter.R
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_register.setOnClickListener {
            finish()
        }
    }
}