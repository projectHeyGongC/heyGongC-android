package com.cctv.heygongc.ui.analysis

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.cctv.heygongc.ActivityMain
import com.cctv.heygongc.data.remote.model.SoundData
import com.cctv.heygongc.data.remote.model.TopDateData
import com.cctv.heygongc.databinding.FragmentAnalysisBinding
import com.cctv.heygongc.ui.adapter.DateRecyclerViewAdapter
import com.cctv.heygongc.ui.adapter.SoundRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AnalysisFragment : Fragment() {
    private var mBinding: FragmentAnalysisBinding? = null
    private val binding get() = mBinding!!
    private val mapDay = mapOf<Int, String>(1 to "Sun", 2 to "Mon", 3 to "Tue", 4 to "Wed", 5 to "Thu", 6 to "Fri", 7 to "Sat")
    private val arrayDate = mutableListOf<TopDateData>()
    private val arraySound = mutableListOf<SoundData>()
    private val bottomSheet = AnalysisBottomSheet()
    private var soundFragment: SoundFragment? = null
    lateinit var activityMain: ActivityMain

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentAnalysisBinding.inflate(inflater, container, false)

        val calendar = Calendar.getInstance()
        binding.textViewDate.text = "${calendar.get((Calendar.DATE))}"
        binding.textViewMonth.text = "${mapDay[calendar.get(Calendar.DAY_OF_WEEK)]}\n${calendar.get(Calendar.MONTH)+1} ${calendar.get(Calendar.YEAR)}"
        arrayDate.add(TopDateData(calendar.get(Calendar.DATE), mapDay[calendar.get(Calendar.DAY_OF_WEEK)]!!))
        repeat (14) {
            calendar.add(Calendar.DATE, -1)
            arrayDate.add(0, TopDateData(calendar.get(Calendar.DATE), mapDay[calendar.get(Calendar.DAY_OF_WEEK)]!!))
        }


//        var date = Date(calendar.timeInMillis)



        val dateRecyclerViewAdapter = DateRecyclerViewAdapter(arrayDate)
        binding.recyclerDate.adapter = dateRecyclerViewAdapter

        // 임의의 데이터
        arraySound.add(SoundData("내방", "오늘 소리가 3번 감지"))
//        arraySound.add(SoundData("거실", "오늘 소리가 1번 감지"))
        val soundRecyclerViewAdapter = SoundRecyclerViewAdapter(arraySound)
        binding.recyclerWhereDevices.adapter = soundRecyclerViewAdapter




        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_END
            }
        }

        smoothScroller.targetPosition = arrayDate.size
        binding.recyclerDate.layoutManager?.startSmoothScroll(smoothScroller)

        // 바텀 시트 알러트 생성
        binding.imageViewCalendar.setOnClickListener {
            bottomSheet.show(requireFragmentManager(), bottomSheet.tag)
        }

//        binding.linearLayoutDevice1.setOnClickListener {
//            moveSoundFrg()
//        }


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