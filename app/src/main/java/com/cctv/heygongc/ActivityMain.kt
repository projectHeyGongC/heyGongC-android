package com.cctv.heygongc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.cctv.heygongc.databinding.ActivityMainBinding
import com.cctv.heygongc.ui.analysis.AnalysisFragment
import com.cctv.heygongc.ui.analysis.SoundFragment
import com.cctv.heygongc.ui.fragment.MonitoringFragment
import com.cctv.heygongc.ui.fragment.PremiumFragment
import com.cctv.heygongc.ui.profile.ProfileFragment

class ActivityMain : AppCompatActivity() {
    lateinit var fm: FragmentManager
    lateinit var fragmentMap: MutableMap<String, Fragment>
    companion object {
//        lateinit var fm: FragmentManager
//        lateinit var fragmentMap: MutableMap<String, Fragment>

//        fun moveFragment(fragment: String) {
//            var fragment = fragmentMap[fragment]
//            Log.e("프래그먼트_1", "${fragment}")
//            fm.beginTransaction().show(fragment!!).commit()
//            fm.beginTransaction().hide(AnalysisFragment()!!).commit()
//            fragmentMap.values.forEach {
//                Log.e("프래그먼트_2", "${it}")
//                if(it != null) fm.beginTransaction().hide(it!!).commit()
//            }
//            Log.e("프래그먼트_3", "${fragment}")
//            fm.beginTransaction().show(fragment!!).commit()
//        }
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




//        binding.bottomNavi.labelVisibilityMode = LABEL_VISIBILITY_LABELED   // label 항상 보이기
        initFragmentMap()
        initBottomNavigation()
    }

    private fun initFragmentMap() {
        fragmentMap = mutableMapOf()
        fragmentMap["monitoring"] = MonitoringFragment()
        fragmentMap["analysis"] = AnalysisFragment()
        fragmentMap["premium"] = PremiumFragment()
        fragmentMap["profile"] = ProfileFragment()
        fragmentMap["sound"] = SoundFragment()

        fm = supportFragmentManager
        fragmentMap.values.forEach {
            Log.e("프래그먼트Map","${it}")
            fm.beginTransaction().add(R.id.fragmentHost,it!!).commit()
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
        monitoringFragment = MonitoringFragment()
        fm.beginTransaction().replace(R.id.fragmentHost,monitoringFragment!!).commit()
//        beforeFragment = R.id.monitoringFragment

        binding.bottomNavi.setOnItemSelectedListener {

            when(it.itemId){
                R.id.monitoringFragment ->{
//                    if(beforeFragment == it.itemId) {   // todo : 같은 네비게이션 버튼 다시 클릭했을때 화면 갱신
//                        fragmentManager.beginTransaction().(R.id.fragmentHost, monitoringFragment!!).commit()
//                    }
                    if(monitoringFragment == null){ // null일때만 한번 만들고 이후에는 생성된 객체를 사용하기 때문에 초기화 안됨
                        monitoringFragment = MonitoringFragment()
                        fm.beginTransaction().add(R.id.fragmentHost,monitoringFragment!!).commit()
                    } else {
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHost, monitoringFragment!!).commit()
                    }
                    if(monitoringFragment != null) fm.beginTransaction().show(monitoringFragment!!).commit()
                    if(analysisFragment != null) fm.beginTransaction().hide(analysisFragment!!).commit()
                    if(premiumFragment != null) fm.beginTransaction().hide(premiumFragment!!).commit()
                    if(profileFragment != null) fm.beginTransaction().hide(profileFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.analysisFragment ->{
                    if(analysisFragment == null){
                        analysisFragment = AnalysisFragment()
                        fm.beginTransaction().add(R.id.fragmentHost,analysisFragment!!).commit()
                    } else {
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHost, analysisFragment!!).commit()
                    }
                    if(monitoringFragment != null) fm.beginTransaction().hide(monitoringFragment!!).commit()
                    if(analysisFragment != null) fm.beginTransaction().show(analysisFragment!!).commit()
                    if(premiumFragment != null) fm.beginTransaction().hide(premiumFragment!!).commit()
                    if(profileFragment != null) fm.beginTransaction().hide(profileFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.premiumFragment ->{
                    if(premiumFragment == null){
                        premiumFragment = PremiumFragment()
                        fm.beginTransaction().add(R.id.fragmentHost,premiumFragment!!).commit()
                    } else {
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHost, premiumFragment!!).commit()
                    }
                    if(monitoringFragment != null) fm.beginTransaction().hide(monitoringFragment!!).commit()
                    if(analysisFragment != null) fm.beginTransaction().hide(analysisFragment!!).commit()
                    if(premiumFragment != null) fm.beginTransaction().show(premiumFragment!!).commit()
                    if(profileFragment != null) fm.beginTransaction().hide(profileFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.profileFragment ->{
                    if(profileFragment == null){
                        profileFragment = ProfileFragment()
                        fm.beginTransaction().add(R.id.fragmentHost,profileFragment!!).commit()
                    } else {
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHost, profileFragment!!).commit()
                    }
                    if(monitoringFragment != null) fm.beginTransaction().hide(monitoringFragment!!).commit()
                    if(analysisFragment != null) fm.beginTransaction().hide(analysisFragment!!).commit()
                    if(premiumFragment != null) fm.beginTransaction().hide(premiumFragment!!).commit()
                    if(profileFragment != null) fm.beginTransaction().show(profileFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                else ->{
                    return@setOnItemSelectedListener true
                }
            }
        }
    }
}