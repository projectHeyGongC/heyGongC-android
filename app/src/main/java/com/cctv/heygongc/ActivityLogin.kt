package com.cctv.heygongc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.cctv.heygongc.adapter.LoginViewPagerAdapter
import com.cctv.heygongc.data.LoginDataViewPager
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

        binding.buttonMoveJoin.setOnClickListener {
            startActivity(Intent(this, ActivityJoin::class.java))
        }

        binding.buttonMoveMain.setOnClickListener {
            startActivity(Intent(this, ActivityMain::class.java))
        }

        var item = ArrayList<LoginDataViewPager>()
        item.add(LoginDataViewPager(resources.getString(R.string.viewpager_page1), R.drawable.login_viewpager1))
        item.add(LoginDataViewPager(resources.getString(R.string.viewpager_page2), R.drawable.login_viewpager2))
        item.add(LoginDataViewPager(resources.getString(R.string.viewpager_page3), R.drawable.login_viewpager3))

        binding.ViewPagerLogin.adapter = LoginViewPagerAdapter(this, item)
        binding.dotsIndicator.attachTo(binding.ViewPagerLogin)

//        binding.ViewPagerLogin.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//
//            }
//        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
        System.exit(0)
    }
}