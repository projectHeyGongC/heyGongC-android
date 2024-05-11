package com.cctv.heygongc

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.data.remote.model.LoginGoogleRequestModel
import com.cctv.heygongc.ui.login.LoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


// ViewModel에서는 직접 Context를 주입하는 것은 권장되지 않습니다. ViewModel에서 Context에 종속성을 가지면 메모리 누수와 같은 문제가 발생할수 있습니다.
// Hilt에서는 ViewModel에 여러개의 주입 생성자를 허용 하지 않습니다.

@HiltViewModel
class SplashViewModel @Inject constructor(
    var loginRepository: LoginRepository
): ViewModel() {

    var flagGoogleAccessToken : MutableLiveData<Int> = MutableLiveData(-1)
    var flagGoogleLogin : MutableLiveData<Int> = MutableLiveData(-1)

    fun getGoogleAccessToken(loginGoogleRequestModel: LoginGoogleRequestModel) {


        try {
            viewModelScope.launch {
                val response = loginRepository.getGoogleAccessToken(loginGoogleRequestModel)

                if (response.isSuccessful) {
                    // 구글 엑세스 토큰 얻었으니 우리 서버 로그인 시도
                    Log.e("토큰", "진입")
                } else {

                }

            }

        } catch (e: ApiException) {
            e.printStackTrace()
            flagGoogleAccessToken.value = 1
        }
    }

    fun googleLogin() {
        Log.e("로그인_1","로그인토큰 : ${Common.loginToken}\n 푸시토큰 : ${Common.fcmToken}")
//        loginRepository.googleLogin(flagGoogleLogin)
    }

}