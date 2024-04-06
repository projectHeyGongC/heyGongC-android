package com.cctv.heygongc.ui.join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.cctv.heygongc.ActivityMain
import com.cctv.heygongc.R
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.data.remote.model.UserLoginRequest
import com.cctv.heygongc.data.remote.model.UserLoginResponse
import com.cctv.heygongc.databinding.ActivityJoinBinding
import com.cctv.heygongc.ui.login.LoginService
import com.cctv.heygongc.util.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityJoin : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    val joinViewModel: JoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.joinViewModel = joinViewModel
        binding.lifecycleOwner = this   // binding과 LoginActivity의 생명주기를 맞춰주기

        setObserve()

    }

    private fun setObserve() {

        joinViewModel.checkBox1.observe(this) {
            binding.buttonComfirm.isEnabled = it && joinViewModel.checkBox2.value!! // 필수 체크박스1,2 가 체크되면 버튼 활성화
            binding.radioButtonAll.isChecked = it && joinViewModel.checkBox2.value!! &&  joinViewModel.checkBox3.value!!
            joinViewModel.radioButton.value = it && joinViewModel.checkBox2.value!! &&  joinViewModel.checkBox3.value!!
        }

        joinViewModel.checkBox2.observe(this) {
            binding.buttonComfirm.isEnabled = it && joinViewModel.checkBox1.value!! // 필수 체크박스1,2 가 체크되면 버튼 활성화
            binding.radioButtonAll.isChecked = it && joinViewModel.checkBox1.value!! &&  joinViewModel.checkBox3.value!!
            joinViewModel.radioButton.value = it && joinViewModel.checkBox1.value!! &&  joinViewModel.checkBox3.value!!
        }

        joinViewModel.checkBox3.observe(this) {
            binding.radioButtonAll.isChecked = it && joinViewModel.checkBox1.value!! &&  joinViewModel.checkBox2.value!!
            joinViewModel.radioButton.value = it && joinViewModel.checkBox1.value!! &&  joinViewModel.checkBox2.value!!
        }

        joinViewModel.confirmButton.observe(this) {
            // 확인버튼 클릭 이벤트
            // 회원가입 api
            LoginService.loginRetrofit("http://15.165.133.184/").signup(
                loginRequest = UserLoginRequest(
                    "testDeviceId",
                    "AOS",
                    "GOOGLE",
                    Common.accessToken,
                    Common.fcmToken,
                    true
                )
            ).enqueue(object : Callback<UserLoginResponse> {
                override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                    Log.e("로그인응답","response : ${response.code()}, ${response.isSuccessful}, ${response.message()}")
                    if (response.isSuccessful){
                        var data: UserLoginResponse? = response.body()
                        if (response.code() == 200) {    // 로그인 성공. 메인화면으로 이동. 여기서 access token, refresh token shared에 저장해야되나?

                            var pm = SharedPreferencesManager(this@ActivityJoin)
                            pm.saveData(Common.ACCESS_TOKEN, data?.accessToken ?: "")
                            pm.saveData(Common.REFRESH_TOKEN, data?.refreshToken ?: "")

                            startActivity(Intent(this@ActivityJoin, ActivityMain::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@ActivityJoin, "회원가입에 실패하였습니다", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        var data: UserLoginResponse? = response.body()
                        Toast.makeText(this@ActivityJoin, "회원가입에 실패하였습니다\n${data?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })

        }

    }




}