package com.cctv.heygongc.ui.login

import android.annotation.SuppressLint
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

//    val loginViewModel: LoginViewModel by viewModels()

    private val loginGoogle: LoginGoogle by lazy {
        LoginGoogle(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


//        ActivitySplash.setStatusBarTransparent(this)

//        binding.viewModel = loginViewModel
//        binding.lifecycleOwner = this


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
            // LoginGoogle에서 startActivityForResult 호출하고 ActivityLogin 화면의 onActivityResult로 받는다
            loginGoogle.signIn(this)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            // 로그인순서 2
            if (requestCode === 1000) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    // get accessToken
                    loginGoogle.handleSignInResult(task)

                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    e.printStackTrace()
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

}