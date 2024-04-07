package com.cctv.heygongc.ui.login

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


// ViewModel에서는 직접 Context를 주입하는 것은 권장되지 않습니다. ViewModel에서 Context에 종속성을 가지면 메모리 누수와 같은 문제가 발생할수 있습니다.
// Hilt에서는 ViewModel에 여러개의 주입 생성자를 허용 하지 않습니다.

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    var loginRepository: LoginRepository
): ViewModel() {

    var flagGoogleAccessToken : MutableLiveData<Int> = MutableLiveData(-1)

//    @Inject
//    lateinit var loginRepository: LoginRepository

//    fun signIn() {
//        // 로그인순서 1
//        val signInIntent: Intent = googleSignInClient.signInIntent
//        (context as Activity).startActivityForResult(signInIntent, 1000)
//        loginGoogle.signIn()
//    }
//
//    fun signOut(activity: Activity) {
//        loginGoogle.signOut()
//    }



    fun getGoogleAccessToken(activity: Activity, completedTask: Task<GoogleSignInAccount>) {
        try {
            val authCode: String? = completedTask.getResult(ApiException::class.java)?.serverAuthCode   // authcode
            loginRepository.getAccessToken(context, flagGoogleAccessToken, authCode!!)
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }







}