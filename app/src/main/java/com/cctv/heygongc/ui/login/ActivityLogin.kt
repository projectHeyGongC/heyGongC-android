package com.cctv.heygongc.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cctv.heygongc.ActivityMain
import com.cctv.heygongc.ActivitySplash
import com.cctv.heygongc.R
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.data.remote.model.LoginPagerData
import com.cctv.heygongc.databinding.ActivityLoginBinding
import com.cctv.heygongc.ui.join.ActivityJoin
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


//    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestServerAuthCode(getString(R.string.google_login_client_id))
//        .requestEmail()
//        .build()
//
//    private val googleSignInClient = GoogleSignIn.getClient(this, gso)

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        // progress bar
        binding.RelativeLayoutPB.visibility = View.VISIBLE

        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            viewModel.getGoogleAccessToken(task)
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
//        binding.buttonMoveJoin.setOnClickListener {
//            startActivity(Intent(this, ActivityJoin::class.java))
//        }
//
//        binding.buttonMoveMain.setOnClickListener {
//            startActivity(Intent(this, ActivityMain::class.java))
//        }

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
                0 -> {  // loginToken으로 login시도
                    viewModel.googleLogin()
                }
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
            when(it) {
                0 -> {  // 로그인 성공. 메인화면으로 이동
                    var intent = Intent(this@ActivityLogin, ActivityMain::class.java)
                    startActivity(intent)
                    finish()
                }
                1 -> {
                    var alertOneButton = AlertOneButton(this@ActivityLogin, "", "로그인에 실패하였습니다\nA06","확인",null)
                    alertOneButton.show()
                    binding.RelativeLayoutPB.visibility = View.GONE
                }
                2 -> {  // 회원가입
                    var intent = Intent(this@ActivityLogin, ActivityJoin::class.java)
                    startActivity(intent)
                    binding.RelativeLayoutPB.visibility = View.GONE
                }
                3 -> {
                    var alertOneButton = AlertOneButton(this@ActivityLogin, "", "로그인에 실패하였습니다\nA07","확인",null)
                    alertOneButton.show()
                    binding.RelativeLayoutPB.visibility = View.GONE
                }
                4 -> {
                    var alertOneButton = AlertOneButton(this@ActivityLogin, "", "로그인에 실패하였습니다\nA08","확인",null)
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

}