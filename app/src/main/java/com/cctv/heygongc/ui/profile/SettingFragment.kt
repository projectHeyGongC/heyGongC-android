package com.cctv.heygongc.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cctv.heygongc.R
import com.cctv.heygongc.databinding.FragmentProfileBinding
import com.cctv.heygongc.databinding.FragmentSettingBinding
import com.cctv.heygongc.ui.fragment.FragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private var mBinding: FragmentSettingBinding? = null
    private val binding get() = mBinding!!

    lateinit var model: FragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSettingBinding.inflate(inflater, container, false)
        model = ViewModelProvider(requireActivity()).get(FragmentViewModel::class.java)     // fragment들 viewmodel 공유


        // todo : 세팅에서 뒤로 누르기 하면 프로필 화면으로 이동하도록 설정, 로그아웃, 회원탈퇴 로직 적용하기



        return binding.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    private fun setObserve() {

    }

}