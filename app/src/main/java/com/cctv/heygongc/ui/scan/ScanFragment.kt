package com.cctv.heygongc.ui.scan

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.cctv.heygongc.ActivityMain
import com.cctv.heygongc.R
import com.cctv.heygongc.databinding.FragmentScanBinding
import com.cctv.heygongc.ui.setdevicename.SetDeviceNameFragment
import com.google.zxing.integration.android.IntentIntegrator
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    val binding: FragmentScanBinding = _binding!!
    private val permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            turnOnCamera()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(
                requireContext(),
                "카메라 권한이 거부되었습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val scanLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val scanRes = IntentIntegrator.parseActivityResult(result.resultCode, data)
                val content = scanRes.contents
                val setDeviceNameFragment = SetDeviceNameFragment().apply {
                    arguments = Bundle().apply {
                        putString("deviceInfo", content)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentHost, setDeviceNameFragment).addToBackStack(null).commit()
            } else {
                Toast.makeText(requireContext(), "QR 인식을 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    private fun turnOnCamera() {
        val integrator = IntentIntegrator(requireActivity())
        with(integrator) {
            setBeepEnabled(false)
            captureActivity = ActivityMain::class.java
        }
        val intent = integrator.createScanIntent()
        scanLauncher.launch(intent)
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
        checkPermission()
    }

    fun checkPermission() {
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용해주세요.")
            .setPermissions(android.Manifest.permission.CAMERA)
            .check()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}