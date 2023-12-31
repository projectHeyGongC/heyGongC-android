package com.cctv.heygongc.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cctv.heygongc.R
import com.cctv.heygongc.databinding.FragmentAnalysisBinding
import com.cctv.heygongc.databinding.FragmentMonitoringBinding
import com.cctv.heygongc.databinding.FragmentPremiumBinding

class PremiumFragment : Fragment() {
    private var mBinding: FragmentPremiumBinding? = null
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPremiumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}