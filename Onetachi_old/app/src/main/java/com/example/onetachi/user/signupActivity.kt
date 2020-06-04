package com.example.onetachi.user

import android.app.Activity
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.onetachi.MainActivity
import com.example.onetachi.R
import com.example.onetachi.auth.api.AuthApi
import com.example.onetachi.data.SignupUser
import com.example.onetachi.retrofit.MyRetrofit
import com.google.android.gms.fido.Fido
import com.google.android.gms.fido.fido2.api.common.*
import kotlinx.android.synthetic.main.activity_signup.*
import okio.ByteString
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.security.SecureRandom
import java.util.*
import kotlin.collections.ArrayList


class signupActivity: AppCompatActivity(){
    private val PREFS_NAME = "auth"
    private val PREF_TOKEN = "token"
    private val RPID = "test-fido2.glitch.com"

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
    private fun signupProcess(){
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
        registerFIDO2(View(this), id)

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

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int, @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("t", "onActivityResult: requestCode: $requestCode")
        Log.d("t", "onActivityResult: resultCode: $resultCode")
        when (resultCode) {
            Activity.RESULT_OK -> if (data!!.hasExtra(Fido.FIDO2_KEY_ERROR_EXTRA)) {
                //handleErrorResponse(data!!.getByteArrayExtra(Fido.FIDO2_KEY_ERROR_EXTRA), requestCode)
            } else if (data!!.hasExtra(Fido.FIDO2_KEY_RESPONSE_EXTRA)) {
                try {
                    /*handleResponse(
                        data!!.getByteArrayExtra(Fido.FIDO2_KEY_RESPONSE_EXTRA),
                        requestCode
                    )*/
                    Log.d("T", "hi")
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
            Activity.RESULT_CANCELED -> {
                Log.d("T", "onActivityResult: RESULT_CANCELED")
                Toast.makeText(this, "Operation canceled...", Toast.LENGTH_LONG).show()
            }
            else -> {
            }
        }
    }

    open fun registerFIDO2(v: View?, id: String): Unit {
        Log.d("T", "registerFIDO2: start!")
        val optionsBuilder =
            PublicKeyCredentialCreationOptions.Builder()
                .setRp(
                    PublicKeyCredentialRpEntity(
                        "test-fido2.glitch.com",
                        "FIDO2demo",
                        null
                    )
                )
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
                .setUser(
                    PublicKeyCredentialUserEntity(
                        id.toByteArray(),
                        id,
                        null,
                        id
                    )
                )
                .setChallenge(challenge()!!)

        // timeout
        //if (!timeout1.getText().toString().matches("")) optionsBuilder.setTimeoutSeconds(timeout1.getText().toString().toDouble())
        val options = optionsBuilder.build()
        val fido2ApiClient = Fido.getFido2ApiClient(applicationContext)
        val result =
            fido2ApiClient.getRegisterIntent(options)
        result.addOnSuccessListener { fido2PendingIntent ->
            if (fido2PendingIntent.hasPendingIntent()) {
                try {
                    fido2PendingIntent.launchPendingIntent(this, REGISTER_REQUEST_CODE)
                } catch (e: SendIntentException) {
                    Log.d("t", "onSuccess: Exception")
                    e.printStackTrace()
                }
            }
        }
        result.addOnFailureListener { e -> e.printStackTrace() }
    }

    /*@Throws(Exception::class)
    private fun handleResponse(
        byteArrayExtra: ByteArray,
        requestCode: Int
    ) {
        val b64KeyHandle: String
        val clientDataJson: String
        val b64AttestationObject: String
        val b64authData: String
        val b64Signature: String
        val b64UserHandle: String
        when (requestCode) {
            REGISTER_REQUEST_CODE -> {
                Log.d("T", "handleResponse: REGISTER_REQUEST_CODE")
                val attestationResponse =
                    AuthenticatorAttestationResponse.deserializeFromBytes(byteArrayExtra)
                b64KeyHandle =
                    Base64.encodeToString(attestationResponse.keyHandle, Base64.DEFAULT)
                clientDataJson = String(attestationResponse.clientDataJSON, UTF_8)
                b64AttestationObject = Base64.encodeToString(
                    attestationResponse.attestationObject,
                    Base64.DEFAULT
                )
                // store keyHandle
                pubkeyId.setText(b64KeyHandle)
                // propagate rpid
                rpId.setText(RPID)
                Log.d("T", "b64KeyHandle: $b64KeyHandle")
                Log.d("T", "clientDataJson: $clientDataJson")
                Log.d("T", "b64AttestationObject: $b64AttestationObject")
                Log.d(
                    "T",
                    "attestationObject:\n" + decodeAttestationObject(attestationResponse.attestationObject)
                )
                registerResult.setText(
                    "b64Keyhandle: " + b64KeyHandle +
                            "\n clientDataJson: " + clientDataJson +
                            "\n b64AttestationObject: " + b64AttestationObject +
                            "\n attestationObject:\n" + decodeAttestationObject(attestationResponse.attestationObject)
                )
            }
            SIGN_REQUEST_CODE -> {
                Log.d("T", "handleResponse: SIGN_REQUEST_CODE")
                val assertionResponse =
                    AuthenticatorAssertionResponse.deserializeFromBytes(byteArrayExtra)
                b64KeyHandle =
                    Base64.encodeToString(assertionResponse.keyHandle, Base64.DEFAULT)
                clientDataJson = String(assertionResponse.clientDataJSON, UTF_8)
                b64Signature =
                    Base64.encodeToString(assertionResponse.signature, Base64.DEFAULT)
                b64UserHandle =
                    if (assertionResponse.userHandle != null) Base64.encodeToString(
                        assertionResponse.userHandle,
                        Base64.DEFAULT
                    ) else ""
                b64authData =
                    Base64.encodeToString(assertionResponse.authenticatorData, Base64.DEFAULT)
                val authDataMap: Map<String, String> = HashMap()
                decodeAuthData(assertionResponse.authenticatorData, authDataMap)
                val authData: String = mapToString(authDataMap)
                Log.d("T", "b64KeyHandle: $b64KeyHandle")
                Log.d("T", "clientDataJson: $clientDataJson")
                Log.d("T", "b64Signature: $b64Signature")
                Log.d("T", "b64UserHandle: $b64UserHandle")
                Log.d("T", "b64authData: $b64authData")
                Log.d("T", "authData:\n$authData")
                signResult.setText(
                    "b64KeyHandle: " + b64KeyHandle +
                            "\n clientDataJson: " + clientDataJson +
                            "\n b64Signature: " + b64Signature +
                            "\n b64UserHandle: " + b64UserHandle +
                            "\n b64authData: " + b64authData +
                            "\n authData:\n" + authData
                )
            }
            else -> Toast.makeText(this, "Unknown status...", Toast.LENGTH_LONG).show()
        }
    }

    private fun handleErrorResponse(
        byteArrayExtra: ByteArray,
        requestCode: Int
    ) {
        val view: TextView =
            if (requestCode == REGISTER_REQUEST_CODE) registerResult else signResult
        val response =
            AuthenticatorErrorResponse.deserializeFromBytes(byteArrayExtra)
        val errorName = response.errorCode.name
        val errorMessage = response.errorMessage
        Log.d("T", "handleErrorResponse: errorName:$errorName")
        Log.d("T", "handleErrorResponse: errorMessage:$errorMessage")
        view.text = ("Error happened. \ncode: " + errorName
                + "\nmessage: " + errorMessage)
    }

    @Throws(Exception::class)
    private fun decodeAttestationObject(attObj: ByteArray): String {
        val dataItems: List<DataItem> = CborDecoder.decode(attObj)
        var result = ""
        if (dataItems.size == 1 && dataItems[0] is Map<*, *>) {
            val attestationMap: MutableMap<String, String> =
                HashMap()
            val attObjMap =
                dataItems[0] as Map<*, *>
            for (key in attObjMap.getKeys()) {
                if (key is UnicodeString) {
                    if ((key as UnicodeString).getString().equals("fmt")) {
                        val value: UnicodeString? = attObjMap[key] as UnicodeString?
                        attestationMap["fmt"] = value.getString()
                    }
                    if ((key as UnicodeString).getString().equals("authData")) {
                        val authData: ByteArray =
                            (attObjMap[key] as ByteString?).getBytes()
                        decodeAuthData(authData, attestationMap)
                        var index = 32
                        if (*//* flags *//*authData[index] and 1 shl 6 != 0) {
                            index += 5
                            val attData = ByteArray(authData.size - index)
                            System.arraycopy(
                                authData,
                                index,
                                attData,
                                0,
                                authData.size - index
                            )
                            var tmpindex = 0
                            if (attData.size < 18) throw Exception("Invalid attData")
                            val aaguid = ByteArray(16)
                            System.arraycopy(attData, 0, aaguid, 0, 16)
                            attestationMap["aaguid"] = Base64.encodeToString(aaguid, Base64.DEFAULT)
                            tmpindex += 16
                            var length: Int = attData[tmpindex++] shl 8 and 0xFF
                            length += attData[tmpindex++] and 0xFF
                            val credentialId = ByteArray(length)
                            System.arraycopy(attData, tmpindex, credentialId, 0, length)
                            attestationMap["credentialId"] =
                                Base64.encodeToString(credentialId, Base64.DEFAULT)
                        }
                    }
                    if ((key as UnicodeString).getString().equals("attStmt")) {
                        attestationMap["attStmt"] = attObjMap[key].toString()
                    }
                }
            }
            result += mapToString(attestationMap)
        }
        return result
    }*/

    private fun challenge(): ByteArray? {
        return SecureRandom.getSeed(16)
    }
}
