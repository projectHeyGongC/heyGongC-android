package com.cctv.heygongc.ui.login

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.cctv.heygongc.R
import com.cctv.heygongc.data.remote.model.LoginGoogleRequestModel
import com.cctv.heygongc.data.remote.model.LoginGoogleResponseModel
import com.cctv.heygongc.data.remote.model.UserLoginRequest
import com.cctv.heygongc.ui.fragment.ActivityJoin
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


// ViewModel에서는 직접 Context를 주입하는 것은 권장되지 않습니다. ViewModel에서 Context에 종속성을 가지면 메모리 누수와 같은 문제가 발생할수 있습니다.
// Hilt에서는 ViewModel에 여러개의 주입 생성자를 허용 하지 않습니다.
@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val loginGoogle: LoginGoogle
) : AndroidViewModel(application) {


    fun signIn() {
        // 로그인순서 1
//        val signInIntent: Intent = googleSignInClient.signInIntent
//        (context as Activity).startActivityForResult(signInIntent, 1000)
        loginGoogle.signIn()
    }

    fun signOut(activity: Activity) {
        loginGoogle.signOut()
    }



    fun getAccessToken(completedTask: Task<GoogleSignInAccount>) {
        loginGoogle.getAccessToken(completedTask)
    }







}