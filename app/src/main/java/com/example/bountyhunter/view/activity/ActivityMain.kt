package com.example.bountyhunter.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bountyhunter.R
import com.example.bountyhunter.extensions.startActivityForResult

class ActivityMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivityForResult<ActivityLogin>(0)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> {

            }
            else -> {

            }
        }
    }
}