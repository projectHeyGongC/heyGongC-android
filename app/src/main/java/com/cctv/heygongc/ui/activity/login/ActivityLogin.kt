package com.cctv.heygongc.ui.activity.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cctv.heygongc.ui.activity.main.ActivityMain
import com.cctv.heygongc.ui.activity.splash.ActivitySplash
import com.cctv.heygongc.R
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.data.local.SharedPreferencesManager
import com.cctv.heygongc.data.remote.model.LoginGoogleRequestModel
import com.cctv.heygongc.data.remote.model.LoginPagerData
import com.cctv.heygongc.databinding.ActivityLoginBinding
import com.cctv.heygongc.ui.activity.join.ActivityJoin
import com.cctv.heygongc.util.AlertOneButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityLogin : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding


//    @Inject lateinit var loginViewModel: LoginViewModel
    val viewModel: LoginViewModel by viewModels()



    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        // progress bar
        binding.RelativeLayoutPB.visibility = View.VISIBLE

        try {


            val authCode = GoogleSignIn.getSignedInAccountFromIntent(result.data).getResult(ApiException::class.java)?.serverAuthCode

            var loginGoogleRequestModel = LoginGoogleRequestModel(
                grant_type = "authorization_code",
                client_id = applicationContext.getString(R.string.google_login_client_id),
                client_secret = applicationContext.getString(R.string.google_login_client_secret),
                code = authCode.orEmpty()
            )
            viewModel.getGoogleAccessToken(loginGoogleRequestModel)
        } catch (e: ApiException) {
            e.printStackTrace()
            binding.RelativeLayoutPB.visibility = View.INVISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        ActivitySplash.setStatusBarTransparent(this, binding.container, Common.navigationBarHeight)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_login_client_id))
            .requestServerAuthCode(getString(R.string.google_login_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        var item = setPagerData()
        binding.ViewPagerLogin.adapter = LoginViewPagerAdapter(this, item)
        binding.dotsIndicator.attachTo(binding.ViewPagerLogin)

        setListener()
        setObserve()
        
        // todo : Activity에서 viewModel로 retrofit 파라미터 데이터 넘기고 viewModel에서 레트로핏 호출, 응답 처리 한후에 데이터값 옵저버로 액티비티에 연결
        // DeviceService Missing binding usage 에러뜸

    }

    override fun onStart() {
        super.onStart()
    
        // 기존의 로그인한 사용자가 있으면 사용자 유지
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        updateUI(account)
    }


    fun setPagerData(): ArrayList<LoginPagerData> {
        var item = ArrayList<LoginPagerData>()
        item.add(
            LoginPagerData(resources.getString(R.string.viewpager_page1),
                R.drawable.login_viewpager1
            )
        )
        item.add(
            LoginPagerData(resources.getString(R.string.viewpager_page2),
                R.drawable.login_viewpager2
            )
        )
        item.add(
            LoginPagerData(resources.getString(R.string.viewpager_page3),
                R.drawable.login_viewpager3
            )
        )
        return item
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        finishAffinity()
        System.exit(0)
    }

    private fun setListener() {

        // 
        binding.buttonMoveJoin.setOnClickListener {
            startActivity(Intent(this, ActivityJoin::class.java))
        }

        binding.buttonMoveMain.setOnClickListener {
            startActivity(Intent(this, ActivityMain::class.java))
        }

        // 구글 로그인 클릭
        binding.ImageViewLoginGoogle.setOnClickListener {
            googleSignInClient.signOut()
            val signInIntent = googleSignInClient.signInIntent
            googleAuthLauncher.launch(signInIntent)
        }
    }

    private fun setObserve() {
        viewModel.flagGoogleAccessToken.observe(this) {
            when(it) {
//                0 -> {  // loginToken으로 login시도
//                    viewModel.googleLogin()
//                }
                1 -> {
                    var alertOneButton = AlertOneButton(this@ActivityLogin, "", "로그인에 실패하였습니다\nA04","확인",null)
                    alertOneButton.show()
                    binding.RelativeLayoutPB.visibility = View.GONE
                }
                2 -> {
                    var alertOneButton = AlertOneButton(this@ActivityLogin, "", "로그인에 실패하였습니다\nA05","확인",null)
                    alertOneButton.show()
                    binding.RelativeLayoutPB.visibility = View.GONE
                }
            }
        }

        viewModel.flagGoogleLogin.observe(this) {
            when (it) {
                0 -> {  // 로그인 성공. 메인화면으로 이동

                    savePreference()
                    var intent = Intent(this@ActivityLogin, ActivityMain::class.java)
                    startActivity(intent)
                    finish()
                }

                1 -> {
                    var alertOneButton =
                        AlertOneButton(this@ActivityLogin, "", "로그인에 실패하였습니다\nA06", "확인", null)
                    alertOneButton.show()
                    binding.RelativeLayoutPB.visibility = View.GONE
                }

                2 -> {  // 회원가입
                    var intent = Intent(this@ActivityLogin, ActivityJoin::class.java)
                    startActivity(intent)
                    binding.RelativeLayoutPB.visibility = View.GONE
                }

                3 -> {
                    var alertOneButton =
                        AlertOneButton(this@ActivityLogin, "", "로그인에 실패하였습니다\nA07", "확인", null)
                    alertOneButton.show()
                    binding.RelativeLayoutPB.visibility = View.GONE
                }

                4 -> {
                    var alertOneButton =
                        AlertOneButton(this@ActivityLogin, "", "로그인에 실패하였습니다\nA08", "확인", null)
                    alertOneButton.show()
                    binding.RelativeLayoutPB.visibility = View.GONE
                }
            }
        }

    }


    private fun moveSignUpActivity() {
        run {
            startActivity(Intent(this@ActivityLogin, ActivityJoin::class.java))
            finish()
        }
    }

    // 프리퍼런스 저장 Activity or Fragment에서 할것
    fun savePreference() {
        var pm = SharedPreferencesManager(this)
        pm.saveData(pm.LOGIN_TOKEN, Common.loginToken ?: "")  // authcode로 얻은 accessToken
        pm.saveData(pm.ACCESS_TOKEN, Common.accessToken ?: "")  // api accessToken
        pm.saveData(pm.REFRESH_TOKEN, Common.refreshToken ?: "")
        pm.saveData(pm.FCM_TOKEN, Common.fcmToken ?: "")
    }

}