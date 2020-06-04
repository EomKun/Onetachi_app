package com.android.onetachi.ui.paperList

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.onetachi.databinding.PaperlistFragmentBinding
import com.android.onetachi.repository.AuthRepository
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_create_q_r.*
import org.jetbrains.anko.toast
import java.util.*

class PaperListFragment: Fragment() {
    private val viewModel: PaperListViewModel by viewModels()
    private lateinit var binding: PaperlistFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PaperlistFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.QRButton.setOnClickListener {
            viewModel.submit()
        }

        binding.QRCreateBtn.setOnClickListener {
            //QR 만들기
            val base = "https://mydata.kro.kr/file?token=cfccba25be812d506b7a9a68d6774b630cf8ffa3f18d4180361511d06cb64e9c";
            qrcode.apply {
                val multiFormatWriter = MultiFormatWriter()
                val hints = Hashtable<EncodeHintType,String>()
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
                val bitMatrix: BitMatrix = multiFormatWriter.encode(base, BarcodeFormat.QR_CODE, 200, 200,hints)
                val barcodeEncoder = BarcodeEncoder()
                val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

                qrcode.setImageBitmap(bitmap)
            }

            // viewModel.requestQR();
        }
    }
}