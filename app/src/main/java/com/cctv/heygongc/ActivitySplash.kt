package com.cctv.heygongc

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.lifecycle.MutableLiveData
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.ui.join.ActivityJoin
import com.cctv.heygongc.ui.login.ActivityLogin
import com.cctv.heygongc.ui.login.LoginRepository
import com.cctv.heygongc.util.AlertOneButton
import com.cctv.heygongc.util.SharedPreferencesManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@AndroidEntryPoint
class ActivitySplash : AppCompatActivity() {

    // Splash에서는 필드 @Inject로 썼음
    @Inject
    lateinit var loginRepository: LoginRepository

    var flagGoogleLogin : MutableLiveData<Int> = MutableLiveData(-1)

    companion object {
        // 상태바까지 화면 확장
        fun setStatusBarTransparent(context: AppCompatActivity, container: View, navigationHeight: Int) {
            context.window.apply {
                setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            }
            if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
                WindowCompat.setDecorFitsSystemWindows(context.window, false)
            }
            setNavigationBarPadding(container, navigationHeight)
        }

        // 하단 네비게이션바 만큼 패딩주기
        private fun setNavigationBarPadding(container: View, navigationHeight: Int) {
            container.setPadding(
                0,
                0,
                0,
                navigationHeight
            )
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var container = findViewById<ConstraintLayout>(R.id.container)
        Common.navigationBarHeight = getNavigationHeight()

        setStatusBarTransparent(this, container, 0)

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

        var sp = SharedPreferencesManager(this)
        Common.loginToken = sp.loadData(Common.LOGIN_TOKEN,"")
        var fcm = sp.loadData(Common.FCM_TOKEN, "")
        if (fcm.isNotEmpty()) Common.fcmToken = fcm

//        accessToken = ""

        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (Common.loginToken != "") {    // 토큰이 이미 있는건 로그인 이력이 있는거기 때문에 로그인시도
                    loginRepository.googleLogin(flagGoogleLogin)
                } else {
                    val intent = Intent(this@ActivitySplash, ActivityLogin::class.java)
                    startActivity(intent)
                }
            }
        }, 2000)

        setObserve()
    }

    private fun setObserve() {
        flagGoogleLogin.observe(this) {
            when(it) {
                -1 -> {}   // 기본값이므로 아무처리 안하기
                0 -> {  // 로그인 성공. 메인화면으로 이동
                    var intent = Intent(this@ActivitySplash, ActivityMain::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    var intent = Intent(this@ActivitySplash, ActivityLogin::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    fun getNavigationHeight(): Int {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
        else 0
    }


}