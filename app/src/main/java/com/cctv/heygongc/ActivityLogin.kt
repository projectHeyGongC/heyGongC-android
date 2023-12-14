package com.cctv.heygongc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cctv.heygongc.databinding.ActivityLoginBinding
import com.cctv.heygongc.databinding.ActivityMainBinding

class ActivityLogin : AppCompatActivity() {

    private var mBinding: ActivityLoginBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        ActivitySplash.setStatusBarTransparent(this)

        binding.buttonMove.setOnClickListener {
            startActivity(Intent(this, ActivityMain::class.java))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
        System.exit(0)
    }
}