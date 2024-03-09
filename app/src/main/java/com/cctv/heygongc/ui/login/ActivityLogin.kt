package com.cctv.heygongc.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cctv.heygongc.ActivityMain
import com.cctv.heygongc.R
import com.cctv.heygongc.data.remote.model.LoginPagerData
import com.cctv.heygongc.databinding.ActivityLoginBinding
import com.cctv.heygongc.ui.fragment.ActivityJoin
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope


class ActivityLogin : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
//    private val binding get() = mBinding!!

    val loginViewModel: LoginViewModel by viewModels()

    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)  // 여기가 오류
            val userName = account?.givenName
            val serverAuth = account?.serverAuthCode
            val id = account?.id
            val email = account?.email
            val idToken = account?.idToken
            Log.e("토큰","${serverAuth}")
//            moveSignUpActivity()

        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    private val loginGoogle: LoginGoogle by lazy {
        LoginGoogle(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


//        ActivitySplash.setStatusBarTransparent(this)

        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this


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

        binding.ViewPagerLogin.adapter = LoginViewPagerAdapter(this, item)
        binding.dotsIndicator.attachTo(binding.ViewPagerLogin)

        addListener()

    }

    override fun onStart() {
        super.onStart()

        // 기존의 로그인한 사용자가 있으면 사용자 유지
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        updateUI(account)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
        System.exit(0)
    }

    private fun addListener() {

        // 
        binding.buttonMoveJoin.setOnClickListener {
            startActivity(Intent(this, ActivityJoin::class.java))
        }

        binding.buttonMoveMain.setOnClickListener {
            startActivity(Intent(this, ActivityMain::class.java))
        }

        // viewModel에서 fun으로 view에 이벤트 연결하고 liveData 변하면 Activity에서 감지해서 화면 이동 하도록 할것
        binding.ImageViewLoginGoogle.setOnClickListener {
            googleSignInClient.signOut()
            val signInIntent = googleSignInClient.signInIntent
            googleAuthLauncher.launch(signInIntent)

            // onActivityResult 사용시
//            loginGoogle.signIn(this)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 로그인순서 2
        Log.e("로그인","resultCode : ${resultCode == RESULT_OK}, requestCode : ${requestCode}")
        if (requestCode === 1000) {
            if (resultCode !== RESULT_OK) {
                return
            }
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                Log.e("로그인_테스트_1","authCode : ${task==null}")
                // Google Sign In was successful, authenticate with Firebase
                loginViewModel.loginGoogle.handleSignInResult(task)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                e.printStackTrace()
                // ...
            }
        }
    }

//    private fun getGoogleClient(): GoogleSignInClient {
//        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
////            .requestScopes(Scope("https://www.googleapis.com/auth/pubsub"))
////            .requestScopes(Scope("https://www.googleapis.com/oauth2/v4/token"))
////            .requestIdToken(getString(R.string.google_login_client_id))
//            .requestServerAuthCode(getString(R.string.google_login_client_id)) // string 파일에 저장해둔 client id 를 이용해 server authcode를 요청한다.
////            .requestEmail() // 이메일도 요청할 수 있다.
//            .build()
//
//        return GoogleSignIn.getClient(this, googleSignInOption)
//    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestScopes(Scope("https://www.googleapis.com/auth/pubsub"))
            .requestServerAuthCode(getString(R.string.google_login_client_id)) // string 파일에 저장해둔 client id 를 이용해 server authcode를 요청한다.
//            .requestEmail() // 이메일도 요청할 수 있다.
            .build()

        return GoogleSignIn.getClient(this, googleSignInOption)
    }

    private fun moveSignUpActivity() {
        run {
            startActivity(Intent(this@ActivityLogin, ActivityJoin::class.java))
            finish()
        }
    }

}