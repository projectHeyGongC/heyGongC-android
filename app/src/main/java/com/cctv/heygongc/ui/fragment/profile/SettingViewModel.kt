package com.cctv.heygongc.ui.fragment.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.data.model.DeviceDetail
import com.cctv.heygongc.data.remote.model.LoginGoogleResponseModel
import com.cctv.heygongc.data.remote.model.UserLoginResponse
import com.cctv.heygongc.data.repository.DeviceRepository
import com.cctv.heygongc.ui.activity.login.LoginRepository
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {

    var flagBackButton: MutableLiveData<Boolean> = MutableLiveData(false)
    var flagLogout: MutableLiveData<Boolean> = MutableLiveData(false)

    fun goBackSettingFragment() {
        flagBackButton.value = true
    }

    fun logout() {
        flagLogout.value = true
    }

    fun deleteAccount() {
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


}