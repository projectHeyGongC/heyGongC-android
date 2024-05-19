package com.cctv.heygongc.ui.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cctv.heygongc.ui.activity.main.ActivityMain
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.databinding.FragmentSettingBinding
import com.cctv.heygongc.data.local.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private var mBinding: FragmentSettingBinding? = null
    private val binding get() = mBinding!!

//    lateinit var model: FragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSettingBinding.inflate(inflater, container, false)
//        model = ViewModelProvider(requireActivity()).get(FragmentViewModel::class.java)     // fragment들 viewmodel 공유
//
//        binding.viewModel = model


        // todo : 세팅에서 뒤로 누르기 하면 프로필 화면으로 이동하도록 설정, 로그아웃, 회원탈퇴 로직 적용하기


        setObserve()

        return binding.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    private fun setObserve() {
//        model.flagLogout.observe(viewLifecycleOwner) {
//            resetPreference()
//
//            var intent = Intent(requireActivity(), ActivityLogin::class.java)
//            startActivity(intent)
//            requireActivity().finish()
//        }
//
//        model.flagGoBack.observe(viewLifecycleOwner) {
//            when (it) {
//                0 -> {
//                    onBackPressed()
//                }
//            }
//
//        }


    }


    private fun setListener() {
        binding.imageViewGoBack
    }

    private fun onBackPressed() {
        ActivityMain.showFragment(ActivityMain.FRAGMENT_PROFILE)
    }

    fun resetPreference() {
        var pm = SharedPreferencesManager(requireContext())
        pm.saveData(pm.LOGIN_TOKEN, "")  // authcode로 얻은 accessToken
        pm.saveData(pm.ACCESS_TOKEN, "")  // api accessToken
        pm.saveData(pm.REFRESH_TOKEN, "")
        pm.saveData(pm.FCM_TOKEN, "")

        Common.loginToken = ""
        Common.fcmToken = ""
    }


}