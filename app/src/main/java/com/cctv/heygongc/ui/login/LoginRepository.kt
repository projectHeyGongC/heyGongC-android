package com.cctv.heygongc.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.cctv.heygongc.R
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.data.remote.model.LoginGoogleRequestModel
import com.cctv.heygongc.data.remote.model.LoginGoogleResponseModel
import com.cctv.heygongc.data.remote.model.UserLoginRequest
import com.cctv.heygongc.ui.fragment.ActivityJoin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(){

//    @Inject
//    lateinit var loginService: LoginService     // Inject 해줬는데 왜 lateinit 오류가 나오지. 필드 인젝 안되는데

    fun getAccessToken(activity: Activity, authCode:String) {
        LoginService.loginRetrofit("https://www.googleapis.com/").getAccessToken(
            request = LoginGoogleRequestModel(
                grant_type = "authorization_code",
                client_id = activity.getString(R.string.google_login_client_id),
                client_secret = activity.getString(R.string.google_login_client_secret),
                code = authCode.orEmpty()
            )
        ).enqueue(object : Callback<LoginGoogleResponseModel> {
            override fun onResponse(call: Call<LoginGoogleResponseModel>, response: Response<LoginGoogleResponseModel>) {
                if(response.isSuccessful) {
                    val accessToken = response.body()?.access_token.orEmpty()
                    login(activity, accessToken)
                } else {
                    Toast.makeText(activity, "로그인 토큰 수신이 실패 하였습니다", Toast.LENGTH_SHORT)
                }
            }
            override fun onFailure(call: Call<LoginGoogleResponseModel>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
            }
        })
    }

    fun login(activity: Activity, accessToken:String){
        LoginService.loginRetrofit("http://15.165.133.184").login(
            loginRequest = UserLoginRequest(
                "testDeviceId",
                "Android",
                "GOOGLE",
                accessToken,
                Common.fcmToken,
                false
            )
        ).enqueue(object :Callback<UserLoginRequest>{
            override fun onResponse(call: Call<UserLoginRequest>, response: Response<UserLoginRequest>) {
                Log.e("로그인응답","response : ${response.code()}, ${response.isSuccessful}, ${response.message()}")
                if (response.isSuccessful){
                    if (response.code() == 204) { // 회원가입 필요
                        signup(activity, accessToken)
                    } else if (response.code() == 200) {    // 로그인 성공. 메인화면으로 이동. 여기서 access token, refresh token shared에 저장해야되나?
                        activity.startActivity(Intent(activity, ActivityJoin::class.java))
                        activity.finish()
                    }
                } else {
                    Toast.makeText(activity, "로그인이 실패하였습니다", Toast.LENGTH_SHORT)
                }
            }
            override fun onFailure(call: Call<UserLoginRequest>, t: Throwable) {
                Log.e("로그인","로그인 실패")
                Log.e(TAG, "sendOnFailure: ${t.fillInStackTrace()}", )
            }
        })
    }

    fun signup(activity: Activity, accessToken:String){
        LoginService.loginRetrofit("http://15.165.133.184/").signup(
            snsType = "google",
            loginRequest = UserLoginRequest(
                "testDeviceId",
                "Android",
                "GOOGLE",
                accessToken,
                Common.fcmToken,
                false
            )
        ).enqueue(object :Callback<UserLoginRequest>{
            override fun onResponse(call: Call<UserLoginRequest>, response: Response<UserLoginRequest>) {
                Log.e("로그인","회원가입 성공, response.isSuccessful : ${response.isSuccessful}, ${response.code()}, ${response.message()}")
                if (response.isSuccessful){
                    if (response.code() == 200) { // 회원가입 완료되었으니. 토큰 shared에 저장하면서 메인화면으로 이동

                    }

                }
            }
            override fun onFailure(call: Call<UserLoginRequest>, t: Throwable) {
                Log.e("로그인","회원가입 실패")
                Log.e(TAG, "sendOnFailure: ${t.fillInStackTrace()}", )
            }
        })
    }



    companion object {
        const val TAG = "LoginRepository"
    }
}