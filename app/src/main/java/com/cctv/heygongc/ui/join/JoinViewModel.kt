package com.cctv.heygongc.ui.join

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cctv.heygongc.ui.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    var loginRepository: LoginRepository
): ViewModel() {

//    @Inject
//    lateinit var loginRepository: LoginRepository

//    var context = getApplication<Application>().applicationContext  // AndroidViewModel() 상속받지 않고 ViewModel() 상속받으면 getApplication() 사용불가.

    var radioButton : MutableLiveData<Boolean> = MutableLiveData(false)
    var checkBox1 : MutableLiveData<Boolean> = MutableLiveData(false)
    var checkBox2 : MutableLiveData<Boolean> = MutableLiveData(false)
    var checkBox3 : MutableLiveData<Boolean> = MutableLiveData(false)

    var flagGoogleSignup : MutableLiveData<Int> = MutableLiveData(-1)

    fun clickRadioButton() {
        radioButton.value = !radioButton.value!!
        if (radioButton.value!!) {
            checkBox1.value = true
            checkBox2.value = true
            checkBox3.value = true
        } else {
            checkBox1.value = false
            checkBox2.value = false
            checkBox3.value = false
        }
    }

    fun clickButton() {
        // 확인버튼 클릭 이벤트
        // 회원가입 api
//        loginRepository.googleSignup(flagGoogleSignup)    // todo


//        LoginService.loginRetrofit("http://15.165.133.184/").signup(
//            loginRequest = UserLoginRequest(
//                "testDeviceId",
//                "AOS",
//                "GOOGLE",
//                Common.loginToken,
//                Common.fcmToken,
//                true
//            )
//        ).enqueue(object : Callback<UserLoginResponse> {
//            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
//                Log.e("로그인응답","response : ${response.code()}, ${response.isSuccessful}, ${response.message()}")
//                if (response.isSuccessful){
//                    var data: UserLoginResponse? = response.body()
//                    if (response.code() == 200) {    // 로그인 성공. 메인화면으로 이동. 여기서 access token, refresh token shared에 저장해야되나?
//                        var pm = SharedPreferencesManager(context)
//                        pm.saveData(Common.ACCESS_TOKEN, data?.accessToken ?: "")
//                        pm.saveData(Common.REFRESH_TOKEN, data?.refreshToken ?: "")
//
//                        flagGoogleSignup.value = 0
//
//                    } else {
//                        flagGoogleSignup.value = 1
//                    }
//                } else {
//                    flagGoogleSignup.value = 2
//                }
//            }
//            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
//                flagGoogleSignup.value = 3
//                t.printStackTrace()
//            }
//        })
    }
}