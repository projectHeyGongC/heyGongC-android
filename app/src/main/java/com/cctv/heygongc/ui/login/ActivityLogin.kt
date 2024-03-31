package com.cctv.heygongc.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActivityLogin : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding


//    @Inject lateinit var loginViewModel: LoginViewModel
    val loginViewModel: LoginViewModel by viewModels()


//    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestServerAuthCode(getString(R.string.google_login_client_id))
//        .requestEmail()
//        .build()
//
//    private val googleSignInClient = GoogleSignIn.getClient(this, gso)

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            loginViewModel.getAccessToken(this, task)
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


//        ActivitySplash.setStatusBarTransparent(this)

        binding.viewModel = loginViewModel
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

        addListener()

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

//    fun getAccessToken(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val authCode: String? = completedTask.getResult(ApiException::class.java)?.serverAuthCode
//            LoginRepository(this).getAccessToken(authCode!!)
//        } catch (e: ApiException) {
//            e.printStackTrace()
//        }
//    }
//
//    fun signIn() {
//        // 로그인순서 1
//        val signInIntent: Intent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, 1000)
//
//    }
//
//    fun signOut() {
//        googleSignInClient.signOut()
//            .addOnCompleteListener {
//                Toast.makeText(this, "로그아웃 되셨습니다!", Toast.LENGTH_SHORT).show()
//            }
//    }
//
//    fun isLogin(context: Context): Boolean {
//        val account = GoogleSignIn.getLastSignedInAccount(context)
//        return if (account == null) false else (true)
//    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
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
            // 구글 로그인
            googleSignInClient.signOut()
            val signInIntent = googleSignInClient.signInIntent
            googleAuthLauncher.launch(signInIntent)

        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == RESULT_OK) {
//            // 로그인순서 2
//            if (requestCode === 1000) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//                try {
//                    // Google Sign In was successful, authenticate with Firebase
//                    // get accessToken
//                    loginViewModel.getAccessToken(this, task)
//
//                } catch (e: ApiException) {
//                    // Google Sign In failed, update UI appropriately
//                    e.printStackTrace()
//                }
//            }
//        }
//    }


    private fun moveSignUpActivity() {
        run {
            startActivity(Intent(this@ActivityLogin, ActivityJoin::class.java))
            finish()
        }
    }

}