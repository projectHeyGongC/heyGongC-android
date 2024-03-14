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
class LoginViewModel (application: Application) : AndroidViewModel(application) {

//    @Inject
    lateinit var loginService: LoginService
    //    var context = getApplication<Application>().applicationContext

    var context = getApplication<Application>().applicationContext as Activity

//    var context = getApplication(get).applicationContext as Activity

    val loginGoogle: LoginGoogle by lazy {
        LoginGoogle(context)
    }

    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(context.getString(R.string.google_login_client_id)) // string 파일에 저장해둔 client id 를 이용해 server authcode를 요청한다. authcode로 access token을 얻는다.
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, googleSignInOption)
    }

    fun signIn() {
        // 로그인순서 1
//        val signInIntent: Intent = googleSignInClient.signInIntent
//        (context as Activity).startActivityForResult(signInIntent, 1000)
        loginGoogle.signIn()
    }

    fun signOut(activity: Activity) {
        googleSignInClient.signOut()
            .addOnCompleteListener {
                Toast.makeText(activity, "로그아웃 되셨습니다!", Toast.LENGTH_SHORT).show()
            }
    }



    fun getAccessToken(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val authCode: String? = completedTask.getResult(ApiException::class.java)?.serverAuthCode
//            LoginRepository(context).getAccessToken(authCode!!)
//
//            loginService.getAccessToken(
//                request = LoginGoogleRequestModel(
//                    grant_type = "authorization_code",
//                    client_id = context.getString(R.string.google_login_client_id),
//                    client_secret = context.getString(R.string.google_login_client_secret),
//                    code = authCode.orEmpty()
//                )
//            ).enqueue(object : Callback<LoginGoogleResponseModel> {
//                override fun onResponse(call: Call<LoginGoogleResponseModel>, response: Response<LoginGoogleResponseModel>) {
//                    if(response.isSuccessful) {
//                        val accessToken = response.body()?.access_token.orEmpty()
//
//                        // third part 서버로 access token 보내기
//                        login(accessToken)
//                    }
//                }
//                override fun onFailure(call: Call<LoginGoogleResponseModel>, t: Throwable) {
//                    Log.e(LoginRepository.TAG, "getOnFailure: ",t.fillInStackTrace() )
//                }
//            })
//        } catch (e: ApiException) {
//            e.printStackTrace()
//        }
        loginGoogle.getAccessToken(completedTask)
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
                if (response.isSuccessful){
                    if (response.code() == 204) { // 회원가입 필요
                        signup(accessToken)
                    } else if (response.code() == 200) {    // 로그인 성공. 메인화면으로 이동. 여기서 access token, refresh token shared에 저장해야되나?
                        context.startActivity(Intent(context, ActivityJoin::class.java))
                        context.finish()
                    }
                }
            }
            override fun onFailure(call: Call<UserLoginRequest.Token>, t: Throwable) {
                Log.e("로그인","로그인 실패")
                Log.e(LoginRepository.TAG, "sendOnFailure: ${t.fillInStackTrace()}", )
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
                Log.e(LoginRepository.TAG, "sendOnFailure: ${t.fillInStackTrace()}", )
            }
        })
    }





}