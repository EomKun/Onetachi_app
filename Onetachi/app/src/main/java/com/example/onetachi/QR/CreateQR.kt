package com.example.onetachi.QR

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.onetachi.R
import com.example.onetachi.data.QrData
import com.example.onetachi.retrofit.MyRetrofit
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_create_q_r.*
import org.jetbrains.anko.alert
import retrofit2.Call
import retrofit2.Response
import java.util.*


class CreateQR : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_q_r)

        val url = intent.getStringExtra("url").toString()

        qrcode.apply {
            val multiFormatWriter = MultiFormatWriter()
            val hints = Hashtable<EncodeHintType,String>()
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
            val bitMatrix: BitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200,hints)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

            qrcode.setImageBitmap(bitmap)
        }

        toServer.setOnClickListener {
            val service = MyRetrofit().service;
            service.qrScan(QrData(url))?.enqueue(object : retrofit2.Callback<QrData>{
                override fun onFailure(call: Call<QrData>, t: Throwable) {
                    alert("연결 오류"){}.show()
                }

                override fun onResponse(call: Call<QrData>, response: Response<QrData>) {
                    Log.d("Response :: ", response?.body().toString())
                    Toast.makeText(this@CreateQR, response.body().toString(), Toast.LENGTH_LONG).show()
                }
            })

        }

    }
}
