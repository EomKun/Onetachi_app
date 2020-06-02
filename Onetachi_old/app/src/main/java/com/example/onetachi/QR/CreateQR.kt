package com.example.onetachi.QR

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
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
import kotlinx.android.synthetic.main.activity_logined.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Response
import java.util.*


class CreateQR : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_q_r)

        // input으로 받은 값
        val newCode = qrInput.text


        //원하는 값의 QR코드 생성 부분
        createNewQR.setOnClickListener {
//            val intent = Intent(this, CreateQR::class.java)
//            Toast.makeText(this@loginedActivity, qrInput.text, Toast.LENGTH_LONG).show()

            if(!qrInput.text.isEmpty()){
                    val multiFormatWriter = MultiFormatWriter()
                    val hints = Hashtable<EncodeHintType,String>()
                    hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
                    val bitMatrix: BitMatrix = multiFormatWriter.encode(newCode.toString(), BarcodeFormat.QR_CODE, 200, 200,hints)
                    val barcodeEncoder = BarcodeEncoder()
                    val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)
//                Toast.makeText(this@CreateQR, newCode.toString(), Toast.LENGTH_LONG).show()
                    qrcode.setImageBitmap(bitmap)
            }
            else{
                alert("값을 입력해주세요"){}.show()
            }
        }


        toServer.setOnClickListener {
            startActivity<WebViewQR>()

        }

    }
}
