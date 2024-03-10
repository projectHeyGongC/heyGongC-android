package com.cctv.heygongc.ui.login

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    //    var context = getApplication<Application>().applicationContext
    var context = getApplication<Application>().applicationContext as Activity

    val loginGoogle: LoginGoogle by lazy {
        LoginGoogle(context)
    }
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }


    init {

    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestScopes(Scope("https://www.googleapis.com/auth/pubsub"))
//            .requestScopes(Scope("https://www.googleapis.com/oauth2/v4/token"))
//            .requestServerAuthCode(getString(R.string.google_login_client_id)) // string 파일에 저장해둔 client id 를 이용해 server authcode를 요청한다.
            .requestEmail() // 이메일도 요청할 수 있다.
            .build()

        return GoogleSignIn.getClient(context, googleSignInOption)
    }






}