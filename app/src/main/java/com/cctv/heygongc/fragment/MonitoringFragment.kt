package com.cctv.heygongc.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cctv.heygongc.R
import com.cctv.heygongc.databinding.FragmentMonitoringBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MonitoringFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MonitoringFragment : Fragment() {

    private var mBinding: FragmentMonitoringBinding? = null
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMonitoringBinding.inflate(inflater, container, false)
        binding.buttonTest.setOnClickListener {
            binding.textViewTest.text = "클릭됨"
        }
        return binding.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()

    }
}