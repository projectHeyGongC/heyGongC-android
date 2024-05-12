package com.cctv.heygongc.ui.activity.login

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoginGoogle @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginService: LoginService
) {

//    @Inject
//    lateinit var loginService: LoginService
//    var activity = context as Activity

//    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestServerAuthCode(activity.getString(R.string.google_login_client_id))
//        .requestEmail()
//        .build()
//
//    private val googleSignInClient = GoogleSignIn.getClient(activity, gso)

//    fun getAccessToken(activity: Activity, completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val authCode: String? = completedTask.getResult(ApiException::class.java)?.serverAuthCode
//            LoginRepository(activity).getAccessToken(authCode!!)
//        } catch (e: ApiException) {
//            e.printStackTrace()
//        }
//    }

//    fun signIn() {
//        // 로그인순서 1
//        val signInIntent: Intent = googleSignInClient.signInIntent
//        activity.startActivityForResult(signInIntent, 1000)
//
//    }

//    fun signOut() {
//        googleSignInClient.signOut()
//            .addOnCompleteListener {
//                Toast.makeText(activity, "로그아웃 되셨습니다!", Toast.LENGTH_SHORT).show()
//            }
//    }

    fun isLogin(context: Context): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return if (account == null) false else (true)
    }

//    fun getAccessToken(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val authCode: String? = completedTask.getResult(ApiException::class.java)?.serverAuthCode
//            loginService.getAccessToken(
//                request = LoginGoogleRequestModel(
//                    grant_type = "authorization_code",
//                    client_id = activity.getString(R.string.google_login_client_id),
//                    client_secret = activity.getString(R.string.google_login_client_secret),
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
//
//    }
//
//    fun login(accessToken:String){
//        loginService.login(
//            snsType = "google",
//            loginRequest = UserLoginRequest(
//                "testDeviceId",
//                "Android",
//                null,
//                UserLoginRequest.Token(
//                    accessToken,
//                    ""
//                )
//            )
//        ).enqueue(object : Callback<UserLoginRequest.Token> {
//            override fun onResponse(call: Call<UserLoginRequest.Token>, response: Response<UserLoginRequest.Token>) {
//                if (response.isSuccessful){
//                    if (response.code() == 204) { // 회원가입 필요
//                        signup(accessToken)
//                    } else if (response.code() == 200) {    // 로그인 성공. 메인화면으로 이동. 여기서 access token, refresh token shared에 저장해야되나?
//                        activity.startActivity(Intent(activity, ActivityJoin::class.java))
//                        activity.finish()
//                    }
//                }
//            }
//            override fun onFailure(call: Call<UserLoginRequest.Token>, t: Throwable) {
//                Log.e("로그인","로그인 실패")
//                Log.e(LoginRepository.TAG, "sendOnFailure: ${t.fillInStackTrace()}", )
//            }
//        })
//    }
//
//    fun signup(accessToken:String){
//        loginService.signup(
//            snsType = "google",
//            loginRequest = UserLoginRequest(
//                "testDeviceId",
//                "Android",
//                true,
//                UserLoginRequest.Token(
//                    accessToken,
//                    ""
//                )
//            )
//        ).enqueue(object : Callback<UserLoginRequest.Token> {
//            override fun onResponse(call: Call<UserLoginRequest.Token>, response: Response<UserLoginRequest.Token>) {
//                Log.e("로그인","회원가입 성공, response.isSuccessful : ${response.isSuccessful}, ${response.code()}, ${response.message()}")
//                if (response.isSuccessful){
//                    if (response.code() == 200) { // 회원가입 완료되었으니. 토큰 shared에 저장하면서 메인화면으로 이동
//
//                    }
//
//                }
//            }
//            override fun onFailure(call: Call<UserLoginRequest.Token>, t: Throwable) {
//                Log.e("로그인","회원가입 실패")
//                Log.e(LoginRepository.TAG, "sendOnFailure: ${t.fillInStackTrace()}", )
//            }
//        })
//    }

}