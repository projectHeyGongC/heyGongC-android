package com.cctv.heygongc.ui.activity.main

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.cctv.heygongc.R
import com.cctv.heygongc.databinding.ActivityMainBinding
import com.cctv.heygongc.ui.fragment.analysis.AnalysisFragment
import com.cctv.heygongc.ui.fragment.analysis.SoundFragment
import com.cctv.heygongc.ui.fragment.monitoring.MonitoringFragment
import com.cctv.heygongc.ui.fragment.PremiumFragment
import com.cctv.heygongc.ui.fragment.profile.ProfileFragment
import com.cctv.heygongc.ui.fragment.profile.SettingFragment
import com.cctv.heygongc.data.local.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityMain : AppCompatActivity() {
//    lateinit var fm: FragmentManager
//    lateinit var fragmentMap: MutableMap<String, Fragment>



    companion object {
        lateinit var fm: FragmentManager
        lateinit var fragmentMap: MutableMap<String, Fragment>
        var NowFragment = ""

        val FRAGMENT_MONITORING = "monitoring"
        val FRAGMENT_ANALYSIS = "analysis"
        val FRAGMENT_PREMIUM = "premium"
        val FRAGMENT_PROFILE = "profile"
        val FRAGMENT_SOUND = "sound"
        val FRAGMENT_SETTING = "setting"
        val FRAGMENT_NOTIFICATION = "notification"

        fun showFragment(fragment: String) {
            NowFragment = fragment
            var fragment = fragmentMap[fragment]
            fragmentMap.values.forEach {
                if(it != null) fm.beginTransaction().hide(it!!).commit()
            }
            fm.beginTransaction().show(fragment!!).commit()
        }
    }


    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
//    private val fragmentManager = supportFragmentManager
    private var monitoringFragment: MonitoringFragment? = null
    private var analysisFragment: AnalysisFragment? = null
    private var premiumFragment: PremiumFragment? = null
    private var profileFragment: ProfileFragment? = null

    private var beforeFragment: Int = R.id.monitoringFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleInactiveAppNotificationIntent(intent)



//        binding.bottomNavi.labelVisibilityMode = LABEL_VISIBILITY_LABELED   // label 항상 보이기

        initFragmentMap()     // todo 이거 주석처리 하면 main 안팅김
        initBottomNavigation()

        var pm = SharedPreferencesManager(this)
        var a = pm.loadData(pm.ACCESS_TOKEN, "")
        var b = pm.loadData(pm.REFRESH_TOKEN, "")

    }

    private fun handleInactiveAppNotificationIntent(intent: Intent?) {
        if (intent?.hasExtra("type") == true) {

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: Intent?) {

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (NowFragment == FRAGMENT_SOUND) {    // sound 프래그먼트에서 뒤로 가기 누르면 analysis 프래그먼트로 이동
            showFragment(FRAGMENT_ANALYSIS)
        } else if (NowFragment == FRAGMENT_SETTING) {
            showFragment(FRAGMENT_PROFILE)
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("앱 종료")
                .setMessage("앱을 종료할까요?")
                .setPositiveButton("종료",
                    DialogInterface.OnClickListener { dialog, id ->
                        ActivityCompat.finishAffinity(this)
                        System.exit(0)
                    })
                .setNegativeButton("취소",null)
            // 다이얼로그를 띄워주기
            builder.show()
        }

    }

    private fun initFragmentMap() {
        fragmentMap = mutableMapOf()
        fragmentMap["monitoring"] = MonitoringFragment()
        fragmentMap["analysis"] = AnalysisFragment()
        fragmentMap["premium"] = PremiumFragment()
        fragmentMap["profile"] = ProfileFragment()
        fragmentMap["sound"] = SoundFragment()
        fragmentMap["setting"] = SettingFragment()    // 세팅이 문제. 세팅 프래그먼트 포함 시키면 main에서 팅김

        fm = supportFragmentManager
        fragmentMap.values.forEach {
            fm.beginTransaction().add(R.id.fragmentHost, it!!).commit()
        }

    }

//    fun goDetail(){
//        val transaction = supportFragmentManager.beginTransaction()  // 트랜잭션을 시작하고 변수에 저장
//        transaction.add(R.id.fragmentContainerView,detailFragment) // 상세 프래그먼트를 fragmentContainerView 레이아웃에 추가
//        transaction.addToBackStack("detail") // 백스택에 담아둠 → 뒤로가기 버튼으로 트랜잭션 전체 제거 가능
//        transaction.commit() // 정상처리되었음을 트랜잭션에 알려 반영
//    }

//    fun goBack(){
//        onBackPressed()
//    }

    private fun initBottomNavigation(){
        // 최초로 보이는 프래그먼트
//        monitoringFragment = MonitoringFragment()
//        fm.beginTransaction().replace(R.id.fragmentHost,monitoringFragment!!).commit()
//        beforeFragment = R.id.monitoringFragment

        // 모니터 http://15.165.133.184/v1/devices api 401 응답 오기 때문에 세팅 화면으로 초기 세팅
        showFragment(FRAGMENT_MONITORING)
//        showFragment(FRAGMENT_SETTING)

        binding.bottomNavi.setOnItemSelectedListener {

            when(it.itemId){
                R.id.monitoringFragment ->{
//                    if(beforeFragment == it.itemId) {   // todo : 같은 네비게이션 버튼 다시 클릭했을때 화면 갱신
//                        fragmentManager.beginTransaction().(R.id.fragmentHost, monitoringFragment!!).commit()
//                    }
//                    if(monitoringFragment == null){ // null일때만 한번 만들고 이후에는 생성된 객체를 사용하기 때문에 초기화 안됨
//                        monitoringFragment = MonitoringFragment()
//                        fm.beginTransaction().add(R.id.fragmentHost,monitoringFragment!!).commit()
//                    } else {
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHost, monitoringFragment!!).commit()
//                    }
//                    if(monitoringFragment != null) fm.beginTransaction().show(monitoringFragment!!).commit()
//                    if(analysisFragment != null) fm.beginTransaction().hide(analysisFragment!!).commit()
//                    if(premiumFragment != null) fm.beginTransaction().hide(premiumFragment!!).commit()
//                    if(profileFragment != null) fm.beginTransaction().hide(profileFragment!!).commit()

                    showFragment(FRAGMENT_MONITORING)

                    return@setOnItemSelectedListener true
                }
                R.id.analysisFragment ->{
//                    if(analysisFragment == null){
//                        analysisFragment = AnalysisFragment()
//                        fm.beginTransaction().add(R.id.fragmentHost,analysisFragment!!).commit()
//                    } else {
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHost, analysisFragment!!).commit()
//                    }
//                    if(monitoringFragment != null) fm.beginTransaction().hide(monitoringFragment!!).commit()
//                    if(analysisFragment != null) fm.beginTransaction().show(analysisFragment!!).commit()
//                    if(premiumFragment != null) fm.beginTransaction().hide(premiumFragment!!).commit()
//                    if(profileFragment != null) fm.beginTransaction().hide(profileFragment!!).commit()

                    showFragment(FRAGMENT_ANALYSIS)

                    return@setOnItemSelectedListener true
                }
                R.id.premiumFragment ->{
//                    if(premiumFragment == null){
//                        premiumFragment = PremiumFragment()
//                        fm.beginTransaction().add(R.id.fragmentHost,premiumFragment!!).commit()
//                    } else {
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHost, premiumFragment!!).commit()
//                    }
//                    if(monitoringFragment != null) fm.beginTransaction().hide(monitoringFragment!!).commit()
//                    if(analysisFragment != null) fm.beginTransaction().hide(analysisFragment!!).commit()
//                    if(premiumFragment != null) fm.beginTransaction().show(premiumFragment!!).commit()
//                    if(profileFragment != null) fm.beginTransaction().hide(profileFragment!!).commit()

                    showFragment(FRAGMENT_PREMIUM)

                    return@setOnItemSelectedListener true
                }
                R.id.profileFragment ->{
//                    if(profileFragment == null){
//                        profileFragment = ProfileFragment()
//                        fm.beginTransaction().add(R.id.fragmentHost,profileFragment!!).commit()
//                    } else {
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHost, profileFragment!!).commit()
//                    }
//                    if(monitoringFragment != null) fm.beginTransaction().hide(monitoringFragment!!).commit()
//                    if(analysisFragment != null) fm.beginTransaction().hide(analysisFragment!!).commit()
//                    if(premiumFragment != null) fm.beginTransaction().hide(premiumFragment!!).commit()
//                    if(profileFragment != null) fm.beginTransaction().show(profileFragment!!).commit()

                    showFragment(FRAGMENT_PROFILE)

                    return@setOnItemSelectedListener true
                }
                else ->{
                    return@setOnItemSelectedListener true
                }
            }
        }
    }


}