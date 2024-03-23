package com.cctv.heygongc.ui.login

import android.content.Context
import android.util.Log
import com.cctv.heygongc.R
import com.cctv.heygongc.data.remote.model.LoginGoogleRequestModel
import com.cctv.heygongc.data.remote.model.LoginGoogleResponseModel
import com.cctv.heygongc.data.remote.model.UserLoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginRepository(val context: Context) {

    @Inject
    lateinit var loginService: LoginService
    private val getAccessTokenBaseUrl = "https://www.googleapis.com"
    private val loginBaseUrl = "http://13.125.159.97"

    fun getAccessToken(authCode:String) {
        loginService.getAccessToken(
            request = LoginGoogleRequestModel(
                grant_type = "authorization_code",
                client_id = context.getString(R.string.google_login_client_id),
                client_secret = context.getString(R.string.google_login_client_secret),
                code = authCode.orEmpty()
            )
        ).enqueue(object : Callback<LoginGoogleResponseModel> {
            override fun onResponse(call: Call<LoginGoogleResponseModel>, response: Response<LoginGoogleResponseModel>) {
                if(response.isSuccessful) {
                    val accessToken = response.body()?.access_token.orEmpty()

                    Log.e("로그인","토큰가져오기 성공")
                    // third part 서버로 access token 보내기
                    login(accessToken)
                }
            }
            override fun onFailure(call: Call<LoginGoogleResponseModel>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
            }
        })
    }

    fun login(accessToken:String){
        loginService.login(
            snsType = "google",
            loginRequest = UserLoginRequest(
                "testDeviceId",
                "Android",
                null,
                UserLoginRequest.Token(
                    accessToken,
                    ""
                )
            )
        ).enqueue(object :Callback<UserLoginRequest.Token>{
            override fun onResponse(call: Call<UserLoginRequest.Token>, response: Response<UserLoginRequest.Token>) {
                Log.e("로그인","로그인 성공, response.isSuccessful : ${response.isSuccessful}, ${response.code()}, ${response.message()}")
                if (response.isSuccessful){
                    if (response.code() == 204) { // 회원가입 필요
                        signup(accessToken)
                    } else if (response.code() == 200) {    // 로그인 성공. 메인화면으로 이동. 여기서 access token, refresh token shared에 저장해야되나?

                    }
                }
            }
            override fun onFailure(call: Call<UserLoginRequest.Token>, t: Throwable) {
                Log.e("로그인","로그인 실패")
                Log.e(TAG, "sendOnFailure: ${t.fillInStackTrace()}", )
            }
        })
    }

    fun signup(accessToken:String){
        loginService.signup(
            snsType = "google",
            loginRequest = UserLoginRequest(
                "testDeviceId",
                "Android",
                true,
                UserLoginRequest.Token(
                    accessToken,
                    ""
                )
            )
        ).enqueue(object :Callback<UserLoginRequest.Token>{
            override fun onResponse(call: Call<UserLoginRequest.Token>, response: Response<UserLoginRequest.Token>) {
                Log.e("로그인","회원가입 성공, response.isSuccessful : ${response.isSuccessful}, ${response.code()}, ${response.message()}")
                if (response.isSuccessful){
                    if (response.code() == 200) { // 회원가입 완료되었으니. 토큰 shared에 저장하면서 메인화면으로 이동

                    }

                }
            }
            override fun onFailure(call: Call<UserLoginRequest.Token>, t: Throwable) {
                Log.e("로그인","회원가입 실패")
                Log.e(TAG, "sendOnFailure: ${t.fillInStackTrace()}", )
            }
        })
    }



    companion object {
        const val TAG = "LoginRepository"
    }
}