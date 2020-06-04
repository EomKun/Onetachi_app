package com.android.onetachi.ui.QR

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.onetachi.R
import com.google.zxing.integration.android.IntentIntegrator

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class QRFragment :Fragment() {
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
//        IntentIntegrator.forSupportFragment(this).setBeepEnabled(false).initiateScan()
        val qrScan = IntentIntegrator(activity)
        qrScan.initiateScan()

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

            displayToast()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result.contents));
            if(intent.resolveActivity(activity!!.packageManager) != null){
                startActivity(intent)
            }







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