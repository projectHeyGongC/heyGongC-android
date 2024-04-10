package com.cctv.heygongc.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cctv.heygongc.R
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.data.remote.model.LoginGoogleRequestModel
import com.cctv.heygongc.data.remote.model.LoginGoogleResponseModel
import com.cctv.heygongc.data.remote.model.UserLoginRequest
import com.cctv.heygongc.data.remote.model.UserLoginResponse
import com.cctv.heygongc.util.SharedPreferencesManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(
    @ApplicationContext private val context: Context
){

//    @Inject
//    lateinit var loginService: LoginService     // Inject 해줬는데 왜 lateinit 오류가 나오지. 필드 인젝 안되는데

    fun getGoogleAccessToken(flagGoogleAccessToken: MutableLiveData<Int>, authCode:String) {
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
                    Common.loginToken = response.body()?.access_token.orEmpty()
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

    fun googleLogin(flagGoogleLogin: MutableLiveData<Int>){
        LoginService.loginRetrofit("http://15.165.133.184/").googleLogin(
            loginRequest = UserLoginRequest(
                "testDeviceId", // todo uuid로 넣기
                "AOS",
                "GOOGLE",
                Common.loginToken,
                Common.fcmToken,
                true
            )
        ).enqueue(object :Callback<UserLoginResponse>{
            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                if (response.isSuccessful){
                    if (response.code() == 200) {    // 로그인 성공. 메인화면으로 이동. 여기서 access token, refresh token shared에 저장해야되나?

                        var data: UserLoginResponse? = response.body()

                        savePreference(data)

                        flagGoogleLogin.value = 0
                    } else {
                        flagGoogleLogin.value = 1
                    }
                } else {
                    if (response.code() == 400) { // 회원가입 필요
                        flagGoogleLogin.value = 2
                    } else {
                        flagGoogleLogin.value = 3
                    }
                }
            }
            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                flagGoogleLogin.value = 4
                t.printStackTrace()
            }
        })
    }

    fun googleSignup(flagGoogleSignup: MutableLiveData<Int>){
        LoginService.loginRetrofit("http://15.165.133.184/").signup(
            loginRequest = UserLoginRequest(
                "testDeviceId",     // todo
                "AOS",
                "GOOGLE",
                Common.loginToken,
                Common.fcmToken,
                true
            )
        ).enqueue(object :Callback<UserLoginResponse>{
            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                Log.e("로그인","회원가입 성공, response.isSuccessful : ${response.isSuccessful}, ${response.code()}, ${response.message()}")
                if (response.isSuccessful){
                    if (response.code() == 200) { // 회원가입 완료되었으니. 토큰 shared에 저장하면서 메인화면으로 이동
                        var data: UserLoginResponse? = response.body()

                        savePreference(data)

                        flagGoogleSignup.value = 0
                    }
                }
            }
            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                t.printStackTrace()
                flagGoogleSignup.value = 1
            }
        })
    }

    fun savePreference(data: UserLoginResponse?) {
        var pm = SharedPreferencesManager(context)
        pm.saveData(Common.LOGIN_TOKEN, Common.loginToken ?: "")  // authcode로 얻은 accessToken
        pm.saveData(Common.ACCESS_TOKEN, data?.accessToken ?: "")  // api accessToken
        pm.saveData(Common.REFRESH_TOKEN, data?.refreshToken ?: "")
        pm.saveData(Common.FCM_TOKEN, Common.fcmToken ?: "")
    }


    companion object {
        const val TAG = "LoginRepository"
    }
}