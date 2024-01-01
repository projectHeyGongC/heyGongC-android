package com.cctv.heygongc

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cctv.heygongc.databinding.ActivityMainBinding
import com.cctv.heygongc.fragment.*
import com.google.android.material.navigation.NavigationBarView.LABEL_VISIBILITY_LABELED

class ActivityMain : AppCompatActivity() {


    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
    private val fragmentManager = supportFragmentManager
    private var monitoringFragment: MonitoringFragment? = null
    private var analysisFragment: AnalysisFragment? = null
    private var premiumFragment: PremiumFragment? = null
    private var profileFragment: ProfileFragment? = null

    private var beforeFragment: Int = R.id.monitoringFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavi.labelVisibilityMode = LABEL_VISIBILITY_LABELED   // label 항상 보이기
        initBottomNavigation()
    }

    private fun initBottomNavigation(){
        // 최초로 보이는 프래그먼트
        monitoringFragment = MonitoringFragment()
        fragmentManager.beginTransaction().replace(R.id.fragmentHost,monitoringFragment!!).commit()
//        beforeFragment = R.id.monitoringFragment

        binding.bottomNavi.setOnItemSelectedListener {

            when(it.itemId){
                R.id.monitoringFragment ->{
//                    if(beforeFragment == it.itemId) {   // todo : 같은 네비게이션 버튼 다시 클릭했을때 화면 갱신
//                        fragmentManager.beginTransaction().(R.id.fragmentHost, monitoringFragment!!).commit()
//                    }
                    if(monitoringFragment == null){ // null일때만 한번 만들고 이후에는 생성된 객체를 사용하기 때문에 초기화 안됨
                        monitoringFragment = MonitoringFragment()
                        fragmentManager.beginTransaction().add(R.id.fragmentHost,monitoringFragment!!).commit()
                    } else {
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHost, monitoringFragment!!).commit()
                    }
                    if(monitoringFragment != null) fragmentManager.beginTransaction().show(monitoringFragment!!).commit()
                    if(analysisFragment != null) fragmentManager.beginTransaction().hide(analysisFragment!!).commit()
                    if(premiumFragment != null) fragmentManager.beginTransaction().hide(premiumFragment!!).commit()
                    if(profileFragment != null) fragmentManager.beginTransaction().hide(profileFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.analysisFragment ->{
                    if(analysisFragment == null){
                        analysisFragment = AnalysisFragment()
                        fragmentManager.beginTransaction().add(R.id.fragmentHost,analysisFragment!!).commit()
                    } else {
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHost, analysisFragment!!).commit()
                    }
                    if(monitoringFragment != null) fragmentManager.beginTransaction().hide(monitoringFragment!!).commit()
                    if(analysisFragment != null) fragmentManager.beginTransaction().show(analysisFragment!!).commit()
                    if(premiumFragment != null) fragmentManager.beginTransaction().hide(premiumFragment!!).commit()
                    if(profileFragment != null) fragmentManager.beginTransaction().hide(profileFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.premiumFragment ->{
                    if(premiumFragment == null){
                        premiumFragment = PremiumFragment()
                        fragmentManager.beginTransaction().add(R.id.fragmentHost,premiumFragment!!).commit()
                    } else {
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHost, premiumFragment!!).commit()
                    }
                    if(monitoringFragment != null) fragmentManager.beginTransaction().hide(monitoringFragment!!).commit()
                    if(analysisFragment != null) fragmentManager.beginTransaction().hide(analysisFragment!!).commit()
                    if(premiumFragment != null) fragmentManager.beginTransaction().show(premiumFragment!!).commit()
                    if(profileFragment != null) fragmentManager.beginTransaction().hide(profileFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.profileFragment ->{
                    if(profileFragment == null){
                        profileFragment = ProfileFragment()
                        fragmentManager.beginTransaction().add(R.id.fragmentHost,profileFragment!!).commit()
                    } else {
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHost, profileFragment!!).commit()
                    }
                    if(monitoringFragment != null) fragmentManager.beginTransaction().hide(monitoringFragment!!).commit()
                    if(analysisFragment != null) fragmentManager.beginTransaction().hide(analysisFragment!!).commit()
                    if(premiumFragment != null) fragmentManager.beginTransaction().hide(premiumFragment!!).commit()
                    if(profileFragment != null) fragmentManager.beginTransaction().show(profileFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                else ->{
                    return@setOnItemSelectedListener true
                }
            }
        }
    }
}