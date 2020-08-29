package com.outlivethesun.floatingwindow

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_float_starter.*

class FloatStarterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_starter)

        btn_start_floating.setOnClickListener {
            startService(Intent(this@FloatStarterActivity, FloatingWindowService::class.java))
        }
    }

}