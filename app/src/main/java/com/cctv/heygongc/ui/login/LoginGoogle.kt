package com.cctv.heygongc.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.cctv.heygongc.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginGoogle(val activity: Activity) {
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestServerAuthCode(activity.getString(R.string.google_login_client_id))
        .requestEmail()
        .build()

    private val googleSignInClient = GoogleSignIn.getClient(activity, gso)

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val authCode: String? = completedTask.getResult(ApiException::class.java)?.serverAuthCode
            LoginRepository(activity).getAccessToken(authCode!!)
        } catch (e: ApiException) {
            Log.w(TAG, "handleSignInResult: error" + e.statusCode)
        }
    }

    fun signIn(activity: Activity) {
        // 로그인순서 1
        val signInIntent: Intent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, 1000)

    }

    fun signOut(activity: Activity) {
        googleSignInClient.signOut()
            .addOnCompleteListener {
                Toast.makeText(activity, "로그아웃 되셨습니다!", Toast.LENGTH_SHORT).show()
            }
    }

    fun isLogin(context: Context): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return if (account == null) false else (true)
    }

    companion object {
        const val TAG = "GoogleLoginService"
    }
}