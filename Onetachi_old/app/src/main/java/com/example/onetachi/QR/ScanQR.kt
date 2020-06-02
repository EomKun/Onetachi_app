package com.example.onetachi.QR

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.onetachi.R
import com.example.onetachi.data.QrData
import com.example.onetachi.retrofit.MyRetrofit
import com.google.zxing.integration.android.IntentIntegrator
import org.jetbrains.anko.alert
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScanQR : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_q_r)



        //        IntentIntegrator(this).initiateScan()
        val qrScan = IntentIntegrator(this)
        qrScan.setCaptureActivity(CaptureForm::class.java)
        qrScan.setOrientationLocked(false) //default값은 세로모드인데 가로로 옮기면 가로로 되게 변경
        qrScan.setPrompt("QR코드")
        qrScan.setBeepEnabled(false)
        qrScan.initiateScan()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val service = MyRetrofit().service;

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(result.contents));
                startActivity(intent)

                service.qrScan(QrData(result.contents))?.enqueue(object : retrofit2.Callback<QrData>{
                    override fun onFailure(call: Call<QrData>, t: Throwable) {
                        alert("no"){}.show()
                    }

                    override fun onResponse(call: Call<QrData>, response: Response<QrData>) {
                        Log.d("Response :: ", response?.body().toString())

                        Toast.makeText(this@ScanQR, response.body().toString(), Toast.LENGTH_LONG).show()
                    }
                })

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}
