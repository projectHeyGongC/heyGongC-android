package com.cctv.heygongc.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.cctv.heygongc.ActivityMain
import com.cctv.heygongc.R
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.data.remote.model.LoginGoogleRequestModel
import com.cctv.heygongc.data.remote.model.LoginGoogleResponseModel
import com.cctv.heygongc.data.remote.model.UserLoginRequest
import com.cctv.heygongc.data.remote.model.UserLoginResponse
import com.cctv.heygongc.ui.join.ActivityJoin
import com.cctv.heygongc.util.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(){

//    @Inject
//    lateinit var loginService: LoginService     // Inject 해줬는데 왜 lateinit 오류가 나오지. 필드 인젝 안되는데

    fun getAccessToken(context: Context, flagGoogleAccessToken: MutableLiveData<Int>, authCode:String) {
        LoginService.loginRetrofit("https://www.googleapis.com/").getAccessToken(
            request = LoginGoogleRequestModel(
                grant_type = "authorization_code",
                client_id = context.getString(R.string.google_login_client_id),
                client_secret = context.getString(R.string.google_login_client_secret),
                code = authCode.orEmpty()
            )
        ).enqueue(object : Callback<LoginGoogleResponseModel> {
            override fun onResponse(call: Call<LoginGoogleResponseModel>, response: Response<LoginGoogleResponseModel>) {
                if(response.isSuccessful) {
                    // authcode로 얻은 accesstoken과 우리서버 login api응답으로 받은 accesstoken 값이 다르다. authcode 토큰 값으로 우리 서버에 보내야 로그인 가능
                    Common.accessToken = response.body()?.access_token.orEmpty()
                    flagGoogleAccessToken.value = 0
                } else {
                    flagGoogleAccessToken.value = 1
                }
            }
            override fun onFailure(call: Call<LoginGoogleResponseModel>, t: Throwable) {
                flagGoogleAccessToken.value = 2
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
            }
        })
    }

    fun login(activity: Activity, accessToken:String){
        LoginService.loginRetrofit("http://15.165.133.184/").login(
            loginRequest = UserLoginRequest(
                "testDeviceId",
                "AOS",
                "GOOGLE",
                accessToken,
                Common.fcmToken,
                true
            )
        ).enqueue(object :Callback<UserLoginResponse>{
            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                Log.e("로그인응답","response : ${response.code()}, ${response.isSuccessful}, ${response.message()}")
                if (response.isSuccessful){
                    if (response.code() == 200) {    // 로그인 성공. 메인화면으로 이동. 여기서 access token, refresh token shared에 저장해야되나?

                        var data: UserLoginResponse? = response.body()

                        var pm = SharedPreferencesManager(activity)
                        pm.saveData(Common.LOGIN_TOKEN, Common.accessToken ?: "")  // authcode로 얻은 accesscode
                        pm.saveData(Common.ACCESS_TOKEN, Common.accessToken ?: "")  // authcode로 얻은 accesscode
                        pm.saveData(Common.REFRESH_TOKEN, data?.refreshToken ?: "")
                        pm.saveData(Common.FCM_TOKEN, Common.fcmToken ?: "")

                        activity.startActivity(Intent(activity, ActivityMain::class.java))
                        activity.finish()
                    }
                } else {
                    if (response.code() == 400) { // 회원가입 필요
                        activity.startActivity(Intent(activity, ActivityJoin::class.java))
                    } else {
                        Toast.makeText(activity, "로그인에 실패했습니다\n코드(a01)", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun signup(activity: Activity, accessToken:String){
        LoginService.loginRetrofit("http://15.165.133.184/").signup(
            loginRequest = UserLoginRequest(
                "testDeviceId",
                "AOS",
                "GOOGLE",
                accessToken,
                Common.fcmToken,
                true
            )
        ).enqueue(object :Callback<UserLoginResponse>{
            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                Log.e("로그인","회원가입 성공, response.isSuccessful : ${response.isSuccessful}, ${response.code()}, ${response.message()}")
                if (response.isSuccessful){
                    if (response.code() == 200) { // 회원가입 완료되었으니. 토큰 shared에 저장하면서 메인화면으로 이동

                    }

                }
            }
            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                Log.e("로그인","회원가입 실패")
                Log.e(TAG, "sendOnFailure: ${t.fillInStackTrace()}", )
            }
        })
    }



    companion object {
        const val TAG = "LoginRepository"
    }
}