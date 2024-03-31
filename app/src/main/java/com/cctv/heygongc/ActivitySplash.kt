package com.cctv.heygongc

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.core.view.WindowCompat
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.ui.login.ActivityLogin
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
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

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("ActivitySplash", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // FCM 등록 토큰 가져오기
            val token = task.result
            Common.fcmToken = token
            Log.d("ActivitySplash token : ", token+"")

        })

        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(this@ActivitySplash, ActivityLogin::class.java)
                startActivity(intent)
            }
        }, 3000)
    }


}