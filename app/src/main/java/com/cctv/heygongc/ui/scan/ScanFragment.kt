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