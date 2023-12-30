package com.example.whetherapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.whetherapp.R
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        //setContentView(R.layout.activity_splash)

      /* val timer = Timer()
        timer.schedule(timerTask { nextScreen() }, 2000)*/ // ver si dejar la manera antigua o dejar la manera en la que android recomienda (la nueva manera no permite anmacion, por ahora solo android 12 lo permite)


    }

    private fun nextScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}