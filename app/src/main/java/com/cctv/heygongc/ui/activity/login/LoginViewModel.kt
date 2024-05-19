package com.cctv.heygongc.ui.activity.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.data.remote.model.LoginGoogleRequestModel
import com.cctv.heygongc.data.remote.model.LoginGoogleResponseModel
import com.cctv.heygongc.data.remote.model.UserLoginRequest
import com.cctv.heygongc.data.remote.model.UserLoginResponse
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


// ViewModel에서는 직접 Context를 주입하는 것은 권장되지 않습니다. ViewModel에서 Context에 종속성을 가지면 메모리 누수와 같은 문제가 발생할수 있습니다.
// Hilt에서는 ViewModel에 여러개의 주입 생성자를 허용 하지 않습니다.

@HiltViewModel
class LoginViewModel @Inject constructor(
    var loginRepository: LoginRepository
): ViewModel() {

    var flagGoogleAccessToken : MutableLiveData<Int> = MutableLiveData(-1)
    var flagGoogleLogin : MutableLiveData<Int> = MutableLiveData(-1)

    fun getGoogleAccessToken(loginGoogleRequestModel: LoginGoogleRequestModel) {


        try {
            viewModelScope.launch {
                val response_accessToken = loginRepository.getGoogleAccessToken(loginGoogleRequestModel)

                if (response_accessToken.isSuccessful) {    // 구글로 부터 로그인토큰 (access token) 얻기 성공

                    // 구글 엑세스 토큰 얻었으니 우리 서버 로그인 시도
                    var data: LoginGoogleResponseModel = response_accessToken.body()!!


//                    var userLoginRequest = UserLoginRequest(
//                        deviceId = Common.deviceId,
//                        deviceOs = "AOS",
//                        snsType = "GOOGLE",
//                        accessToken = data.access_token,
//                        fcmToken = Common.fcmToken,
//                        ads = true
//                    )
//                    Common.loginToken = data.access_token   // 구글로 부터 얻은 토큰

//                    val response_login = loginRepository.googleLogin(userLoginRequest)

                    val response_login = googleLogin(data)

                    if (response_login.isSuccessful) {  // 우리서버 응답 성공
                        when (response_login.code()) {
                            200 -> {
                                var data: UserLoginResponse = response_login.body()!!

                                Common.accessToken = data.accessToken
                                Common.refreshToken = data.refreshToken

                                flagGoogleLogin.value = 0
                            }
                            else -> {
                                flagGoogleLogin.value = 1
                            }
                        }
                    } else {    // 우리서버 응답 실패
                        when (response_login.code()) {
                            400 -> {
                                flagGoogleLogin.value = 2
                            }
                            else -> {
                                flagGoogleLogin.value = 3
                            }
                        }
                    }
                } else {
                    flagGoogleAccessToken.value = 1 // todo 여기서 throw로 아래의 catch문으로 보낼수 있나?
                    Log.e("LoginViewModel","accessToken 얻기 실패")
                }

            }

        } catch (e: ApiException) {
            e.printStackTrace()
            flagGoogleAccessToken.value = 1
        }
    }

    suspend fun googleLogin(data: LoginGoogleResponseModel): Response<UserLoginResponse> {
        return withContext(Dispatchers.Default) {
            // 비동기 작업을 처리하고 결과를 반환

            var userLoginRequest = UserLoginRequest(
                deviceId = Common.deviceId,
                deviceOs = "AOS",
                snsType = "GOOGLE",
                accessToken = data.access_token,
                fcmToken = Common.fcmToken,
                ads = true
            )
            Common.loginToken = data.access_token

            return@withContext loginRepository.googleLogin(userLoginRequest)
        }
    }

}