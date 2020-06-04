package com.android.onetachi.ui.qr

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.onetachi.BuildConfig
import com.android.onetachi.R
import com.android.onetachi.api.AddHeaderInterceptor
import com.android.onetachi.api.ApiException
import com.android.onetachi.api.AuthApi
import com.android.onetachi.databinding.QrFragmentBinding
import com.android.onetachi.repository.AuthRepository
import com.google.gson.stream.JsonWriter
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_create_q_r.*
import okhttp3.*
import java.io.StringWriter
import java.util.*
import java.util.concurrent.TimeUnit

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class QRFragment: Fragment() {
    private var toast: String? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        displayToast()

    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.qr_fragment, container, false)

//        IntentIntegrator.forFragment(Fragment.this).setBeepEnabled(false).initiateScan()
//        IntentIntegrator.forSupportFragment(this).setBeepEnabled(false).setOrientationLocked(false).initiateScan()

        //QR 만들기
        val base = "https://mydata.kyo.kr/file?token=cfccba25be812d506b7a9a68d6774b630cf8ffa3f18d4180361511d06cb64e9c";
        qrcode.apply {
            val multiFormatWriter = MultiFormatWriter()
            val hints = Hashtable<EncodeHintType,String>()
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
            val bitMatrix: BitMatrix = multiFormatWriter.encode(base, BarcodeFormat.QR_CODE, 200, 200,hints)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

            qrcode.setImageBitmap(bitmap)
        }


        return view
    }

    private fun displayToast() {
        if (activity != null && toast != null) {
            Toast.makeText(activity, toast, Toast.LENGTH_LONG).show()
            toast = null
        }
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {


        val result =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            toast = if (result.contents == null) {
                "Cancelled from fragment"
            } else {
                "Scanned from fragment: " + result.contents
            }

            val token = "token"
            displayToast()
            //ToDo 토큰값 전달해줄 것.
        }
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QRFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}