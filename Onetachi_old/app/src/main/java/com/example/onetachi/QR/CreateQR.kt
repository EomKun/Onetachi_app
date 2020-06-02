package com.example.onetachi.QR

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.onetachi.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_create_q_r.*
import java.util.*


class CreateQR : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_q_r)

        qrcode.apply {
            val multiFormatWriter = MultiFormatWriter()
            val hints = Hashtable<EncodeHintType,String>()
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
            val bitMatrix: BitMatrix = multiFormatWriter.encode("http://www.naver.com", BarcodeFormat.QR_CODE, 200, 200,hints)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

            qrcode.setImageBitmap(bitmap)
        }
    }
}
