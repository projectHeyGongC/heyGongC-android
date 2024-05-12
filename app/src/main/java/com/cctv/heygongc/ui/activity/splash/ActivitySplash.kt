package com.cctv.heygongc.ui.activity.splash

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.cctv.heygongc.R
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.ui.activity.login.ActivityLogin
import com.cctv.heygongc.ui.activity.login.LoginRepository
import com.cctv.heygongc.data.local.SharedPreferencesManager
import com.cctv.heygongc.data.remote.model.UserLoginRequest
import com.cctv.heygongc.data.remote.model.UserLoginResponse
import com.cctv.heygongc.ui.activity.main.ActivityMain
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@AndroidEntryPoint
class ActivitySplash : AppCompatActivity() {

    // todo view model 써야하나?

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



        setObserve()
    }

    override fun onResume() {
        super.onResume()

        // todo : Splash viewmodel에서 hilt사용 가능하면 splashViewmodel에서 인터넷 연결 상태 확인 클래스 hilt로 주입해서 사용 할것

        // 디바이스 식별자. 갱신 되는 조건
        // 1. 디바이스가 최초 부팅 시에 생성됨
        // 2. 초기화 전까지는 삭제 되지 않고 저장되어 있어 디바이스 식별에 유용
        // 3. 기기를 초기화하면 값이 바뀜
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        Common.deviceId = deviceId
        Log.e("uuid", "uuid : ${deviceId}")
        Log.e("오류_1", "진입")

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("ActivitySplash", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // FCM 등록 토큰 가져오기
            val token = task.result
            Common.fcmToken = token

            if (token.isEmpty()) Toast.makeText(this@ActivitySplash, "토큰값 없음", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this@ActivitySplash, "토큰값 발급완료", Toast.LENGTH_SHORT).show()
            Log.d("ActivitySplash 푸시토큰 : ", token+"\ntoken빈값 : ${token==""}\nempty : ${token.isEmpty()} ")  // todo : 푸시토큰 발급 안될때 A07 뜬다. 푸시토큰 발급 안되는 경우 보완 할것. 푸시토큰 발급 무조건 되도록 설정 안되나?

        })
        Log.e("오류_2", "진입")

//        var token = FirebaseMessaging.getInstance().token.result
//        Log.e("fcm token", "token : ${token}")

        var pm = SharedPreferencesManager(this)
        Common.loginToken = pm.loadData(pm.LOGIN_TOKEN,"")
        var fcm = pm.loadData(pm.FCM_TOKEN, "")
        if (fcm != "") {
            Common.fcmToken = fcm
        }
        Log.e("오류_3", "진입")

        Timer().schedule(object : TimerTask() {
            override fun run() {
                lifecycleScope.launch {
                    Log.e("로그인토큰111","로그인토큰 : ${Common.loginToken}\n푸시토큰 : ${Common.fcmToken}")
                    if (Common.loginToken != "") {    // 토큰이 이미 있는건 로그인 이력이 있는거기 때문에 로그인시도
//                        var userLoginRequest = UserLoginRequest(
//                            deviceId = Common.deviceId,
//                            deviceOs = "AOS",
//                            snsType = "GOOGLE",
//                            accessToken = Common.loginToken,
//                            fcmToken = Common.fcmToken,
//                            ads = true
//                        )
//
//                        val response = loginRepository.googleLogin(userLoginRequest)

                        Log.e("힐트_1","진입")
                        val response = googleLogin()

                        Log.e("스플래시","${response.isSuccessful}")    // todo : splash 에서 에러가 나는데 Log 찍어서 어디서 오류 나는지 확인할것
                        if (response.isSuccessful) {
                            // 프리퍼런스 데이터 저장 하고 메인으로 이동
//                            var intent = Intent(this@ActivitySplash, ActivityMain::class.java)
//                            startActivity(intent)
//                            finish()
                        } else {
//                            var intent = Intent(this@ActivitySplash, ActivityLogin::class.java)
//                            startActivity(intent)
//                            finish()
                        }
                    } else {
                        Log.e("힐트_2","진입")
                        val intent = Intent(this@ActivitySplash, ActivityLogin::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }, 2000)

        Log.e("로그인_4 푸시토큰","${Common.fcmToken}")
        Log.e("오류_4", "진입")
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

    suspend fun googleLogin(): Response<UserLoginResponse> {
        return withContext(Dispatchers.Default) {
            // 비동기 작업을 처리하고 결과를 반환

            var userLoginRequest = UserLoginRequest(
                deviceId = Common.deviceId,
                deviceOs = "AOS",
                snsType = "GOOGLE",
                accessToken = Common.loginToken,
                fcmToken = Common.fcmToken,
                ads = true
            )

            return@withContext loginRepository.googleLogin(userLoginRequest)
        }
    }

    fun getNavigationHeight(): Int {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
        else 0
    }


}