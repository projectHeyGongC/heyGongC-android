package com.cctv.heygongc.ui.fragment

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.WindowCompat
import com.cctv.heygongc.R
import com.cctv.heygongc.ui.login.ActivityLogin
import java.util.Timer
import java.util.TimerTask

class ActivitySplash : AppCompatActivity() {

    companion object {
        fun setStatusBarTransparent(context: AppCompatActivity) {   // 상태바까지 화면 확장
            context.window.apply {
                setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            }
            if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
                WindowCompat.setDecorFitsSystemWindows(context.window, false)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        setStatusBarTransparent(this)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(this@ActivitySplash, ActivityLogin::class.java)
                startActivity(intent)
            }
        }, 3000)
    }


}