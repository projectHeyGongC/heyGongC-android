package com.cctv.heygongc.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.cctv.heygongc.R
import com.cctv.heygongc.ui.activity.main.ActivityMain
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.databinding.FragmentSettingBinding
import com.cctv.heygongc.data.local.SharedPreferencesManager
import com.cctv.heygongc.ui.activity.login.ActivityLogin
import com.cctv.heygongc.ui.fragment.monitoring.MonitoringViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private var mBinding: FragmentSettingBinding? = null
    private val binding get() = mBinding!!

//    lateinit var model: FragmentViewModel



    private val viewModel: SettingViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        mBinding = FragmentSettingBinding.inflate(inflater, container, false)
//        binding.viewModel = viewModel
//        viewLifecycleOwner = this

//        model = ViewModelProvider(requireActivity()).get(FragmentViewModel::class.java)     // fragment들 viewmodel 공유
//
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.viewModel = viewModel   // viewmodel 생명주기 따로 설정안해줘도되나?



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

        viewModel.flagBackButton.observe(viewLifecycleOwner) {
            if (it) {
                ActivityMain.showFragment(ActivityMain.FRAGMENT_PROFILE)
            }
        }

        viewModel.flagLogout.observe(viewLifecycleOwner) {
            if (it) {
                resetPreference()
                var intent = Intent(activity, ActivityLogin::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }




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

        Common.loginToken = ""
    }


}