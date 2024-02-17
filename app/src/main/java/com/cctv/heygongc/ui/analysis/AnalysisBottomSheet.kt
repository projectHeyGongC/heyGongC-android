package com.cctv.heygongc.ui.analysis

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.CalendarView.OnDateChangeListener
import com.cctv.heygongc.databinding.BottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AnalysisBottomSheet : BottomSheetDialogFragment() {
    private var mBinding: BottomSheetDialogBinding? = null
    private val binding get() = mBinding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = BottomSheetDialogBinding.inflate(inflater, container, false)

        binding.calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            Log.e("선택된 날짜", String.format("%d / %d / %d", year, month + 1, dayOfMonth))
        })



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 클릭 이벤트
//        view?.findViewById<Button>(R.id.button_bottom_sheet)?.setOnClickListener {
//            dismiss()
//        }
    }
}