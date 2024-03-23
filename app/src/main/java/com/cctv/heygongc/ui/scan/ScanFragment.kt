package com.cctv.heygongc.ui.scan

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.cctv.heygongc.Manifest
import com.cctv.heygongc.databinding.FragmentScanBinding

const val CAMERA_PERMISSION_CODE = 1001

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    val binding: FragmentScanBinding = _binding!!
    private val cameraPermissionLauncher : ActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // 권한이 허용된 경우에 실행할 코드
        } else {
            // 권한이 거부된 경우에 실행할 코드

            // ActivityCompat.shouldShowRequestPermissionRationale
            //  → 사용자가 권한 요청을 명시적으로 거부한 경우 true를 반환한다.
            //	→ 사용자가 다시 묻지 않음 선택한 경우 false를 반환한다.
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA)) {
                // 권한 요청에 대한 이유를 사용자에게 설명하는 Dialog를 표시
                AlertDialog.Builder(requireContext())
                    .setTitle("권한 요청")
                    .setMessage("카메라 권한이 필요합니다.")
                    .setPositiveButton("확인") { _, _ ->
                        requestCameraPermission.launch(Manifest.permission.CAMERA)
                    }
                    .setNegativeButton("취소") { _, _ ->
                        // Dialog에서 취소 버튼을 누른 경우에 실행할 코드
                    }
                    .show()
            } else {
                // 사용자가 권한 요청 다이얼로그에서 "다시 묻지 않음" 옵션을 선택한 경우에 실행할 코드
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
/*        // Register the permissions callback, which handles the user's response to the
        // system permissions dialog. Save the return value, an instance of
        // ActivityResultLauncher. You can use either a val, as shown in this snippet,
        // or a lateinit var in your onAttach() or onCreate() method.
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }*/

        if (Build.VERSION.SDK_INT < 30) {
            oldVersionRequestPermission()
        }
    }

    private fun oldVersionRequestPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                // Camera permission denied, handle accordingly
                // You may inform the user about the importance of the permission
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}