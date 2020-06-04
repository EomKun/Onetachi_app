package com.example.onetachi.user

import android.content.Context
import android.content.IntentSender
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.onetachi.MainActivity
import com.example.onetachi.R
import com.example.onetachi.auth.api.AuthApi
import com.example.onetachi.data.SignupUser
import com.example.onetachi.retrofit.MyRetrofit
import com.google.android.gms.fido.Fido
import com.google.android.gms.fido.fido2.Fido2ApiClient
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class signupActivity: AppCompatActivity(){
    private val PREFS_NAME = "auth"
    private val PREF_TOKEN = "token"

    private val api: AuthApi = AuthApi()
    //private val prefs: SharedPreferences = applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val REGISTER_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // sign up
        nextButton.setOnClickListener {
            signupProcess()
        }
    }

    /*
     * 회원 가입
     */
    fun signupProcess(){
        val id = signupIdText.text.toString()
        val regNum1 = signupRegNumText1.text.toString()
        val regNum2 = signupRegNumText2.text.toString()

        // email, 주민번호 검사
        if(!Patterns.EMAIL_ADDRESS.matcher(id).matches()){
            toast("올바른 이메일 형식이 아닙니다")
            signupIdText.setText("")
            return
        }

        if(regNum1.length != 6 || regNum2.length != 7){
            toast("올바른 주민번호 형식이 아닙니다")
            signupRegNumText1.setText("")
            signupRegNumText2.setText("")
            return
        }

        // TODO : Fido2를 이용한 회원 가입
        // 사용자는 기본 정보를 입력 후 nextbutton을 눌렀을 때
        // Fido2를 이용해 지문을 등록하여 가입
        try {
            val token = PREF_TOKEN//prefs.getString(PREF_TOKEN, null)!!

            // TODO(1): Call the server API: /registerRequest
            // - Use api.registerRequest to get a PublicKeyCredentialCreationOptions.
            // - Save the challenge for later use in registerResponse.
            // - Call fido2ApiClient.getRegisterIntent and create an intent to generate a
            //   new credential.
            // - Pass the intent back to the `result` LiveData so that the UI can open the
            //   fingerprint dialog.

            val fido2ApiClient = Fido.getFido2ApiClient(this)
            // Call the API.
            val (options, challenge) = api.registerRequest(token)
            // Use getRegisterIntent to get an Intent to
            // open the fingerprint dialog.
            val result = fido2ApiClient.getRegisterIntent(options)
            result.addOnSuccessListener { fido2PendingIntent ->
                if (fido2PendingIntent.hasPendingIntent()) {
                    try {
                        fido2PendingIntent.launchPendingIntent(this, REGISTER_REQUEST_CODE)
                    } catch (e: IntentSender.SendIntentException) {
                        toast(e.printStackTrace().toString())
                    }
                }
            }
            result.addOnFailureListener { e -> toast(e.printStackTrace().toString()) }
        } catch (e: Exception) {
            Log.e("request", "Cannot call registerRequest", e)
        }
        /*val optionsBuilder =
            PublicKeyCredentialCreationOptions.Builder()
                .setRp(PublicKeyCredentialRpEntity(BuildConfig.RPID, "test-fido2", null))
                .setParameters(
                    ArrayList<PublicKeyCredentialParameters>(
                        Arrays.asList(
                            PublicKeyCredentialParameters(
                                PublicKeyCredentialType.PUBLIC_KEY.toString(),
                                EC2Algorithm.ES256.algoValue
                            ),
                            PublicKeyCredentialParameters(
                                PublicKeyCredentialType.PUBLIC_KEY.toString(),
                                RSAAlgorithm.RS256.algoValue
                            )
                        )
                    )
                )
                .setUser(PublicKeyCredentialUserEntity(id.toByteArray(), id, null, "Sample"))
                .setChallenge(SecureRandom.getSeed(16))

        val fido2ApiClient = Fido.getFido2ApiClient(applicationContext)
        val result =
            fido2ApiClient.getRegisterIntent(optionsBuilder.build())
        result.addOnSuccessListener { fido2PendingIntent ->
            if (fido2PendingIntent.hasPendingIntent()) {
                try {
                    fido2PendingIntent.launchPendingIntent(this, REGISTER_REQUEST_CODE)
                } catch (e: SendIntentException) {
                    toast(e.printStackTrace().toString())
                }
            }
        }
        result.addOnFailureListener { e -> toast(e.printStackTrace().toString()) }*/

        MyRetrofit().service.signupUser(SignupUser(id, regNum1, regNum2))?.enqueue(object : retrofit2.Callback<SignupUser>{
            override fun onFailure(call: retrofit2.Call<SignupUser>?, t: Throwable?) {
                toast("회원 가입 오류")
            }

            override fun onResponse(call: retrofit2.Call<SignupUser>,
                                    response: retrofit2.Response<SignupUser>){
                val result = response.body()

                //toast("회원가입 완료")
                startActivity<MainActivity>()
            }
        })
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> if (data!!.hasExtra(Fido.FIDO2_KEY_ERROR_EXTRA)) {
                toast(data.getByteArrayExtra(Fido.FIDO2_KEY_ERROR_EXTRA).toString() + " " + requestCode.toString())
                //handleErrorResponse(data.getByteArrayExtra(Fido.FIDO2_KEY_ERROR_EXTRA), requestCode)
            } else if (data.hasExtra(Fido.FIDO2_KEY_RESPONSE_EXTRA)) {
                try {
                    toast(data.getByteArrayExtra(Fido.FIDO2_KEY_ERROR_EXTRA).toString() + " " + requestCode.toString())
                    *//*handleResponse(
                        data.getByteArrayExtra(Fido.FIDO2_KEY_RESPONSE_EXTRA),
                        requestCode
                    )*//*
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            Activity.RESULT_CANCELED -> {
                Toast.makeText(this, "Operation canceled...", Toast.LENGTH_LONG).show()
            }
            else -> { }
        }
    }*/
}
