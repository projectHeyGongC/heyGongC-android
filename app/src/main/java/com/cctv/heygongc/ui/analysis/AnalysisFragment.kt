package com.cctv.heygongc.ui.analysis

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView.OnDateChangeListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.cctv.heygongc.ActivityMain
import com.cctv.heygongc.R
import com.cctv.heygongc.data.TopDateData
import com.cctv.heygongc.databinding.FragmentAnalysisBinding
import com.cctv.heygongc.ui.adapter.DateRecyclerViewAdapter
import com.cctv.heygongc.ui.fragment.MonitoringFragment
import java.util.Calendar


class AnalysisFragment : Fragment() {
    private var mBinding: FragmentAnalysisBinding? = null
    private val binding get() = mBinding!!
    private val mapDay = mapOf<Int, String>(1 to "Sun", 2 to "Mon", 3 to "Tue", 4 to "Wed", 5 to "Thu", 6 to "Fri", 7 to "Sat")
    private val arrayListDate = mutableListOf<TopDateData>()
    private val bottomSheet = AnalysisBottomSheet()
    private var soundFragment: SoundFragment? = null
    lateinit var activityMain: ActivityMain

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAnalysisBinding.inflate(inflater, container, false)



        // todo : 데이터를 어떻게 넣어 줄것인가. 오늘을 기준으로 2주전까지 데이터를 받아와야함. 소리감지 recyclerview로 다시 만들것.
        val calendar = Calendar.getInstance()
        binding.textViewDate.text = "${calendar.get((Calendar.DATE))}"
        binding.textViewMonth.text = "${mapDay[calendar.get(Calendar.DAY_OF_WEEK)]}\n${calendar.get(Calendar.MONTH)+1} ${calendar.get(Calendar.YEAR)}"
        arrayListDate.add(TopDateData(calendar.get(Calendar.DATE), mapDay[calendar.get(Calendar.DAY_OF_WEEK)]!!))
        repeat (14) {
            calendar.add(Calendar.DATE, -1)
            arrayListDate.add(0, TopDateData(calendar.get(Calendar.DATE), mapDay[calendar.get(Calendar.DAY_OF_WEEK)]!!))
        }


//        var date = Date(calendar.timeInMillis)



        val dateRecyclerViewAdapter = DateRecyclerViewAdapter(arrayListDate)
        binding.recyclerViewDate.adapter = dateRecyclerViewAdapter

        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_END
            }
        }

        smoothScroller.targetPosition = arrayListDate.size
        binding.recyclerViewDate.layoutManager?.startSmoothScroll(smoothScroller)

        binding.imageViewCalendar.setOnClickListener {
            // 바텀 시트 알러트 생성
            bottomSheet.show(requireFragmentManager(), bottomSheet.tag)
        }

        binding.linearLayoutDevice1.setOnClickListener {
            moveSoundFrg()
        }


        return binding.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override fun onAttach(context: Activity) {
        super.onAttach(context)
        activityMain = context as ActivityMain
    }

    fun moveSoundFrg() {
        ActivityMain.showFragment(ActivityMain.FRAGMENT_SOUND)
    }
}