package com.android.onetachi.ui.paperList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.onetachi.R
import com.android.onetachi.databinding.PaperlistFragmentBinding
import com.android.onetachi.ui.QR.QRFragment
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.paperlist_fragment.*

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
        QRButton.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.QRfragmentView, QRFragment()).commit()
        }

    }

    class ScanFragment : Fragment() {
        private var toast: String? = null
        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            displayToast()
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view: View = inflater.inflate(R.layout.qr_fragment, container, false)
            val scan =
                view.findViewById<Button>(R.id.QRButton)
            scan.setOnClickListener { v: View? -> scanFromFragment() }
            return view
        }

        fun scanFromFragment() {
//            IntentIntegrator.forFragment(ScanFragment).initiateScan()
//            IntentIntegrator.forSupportFragment(this).initiateScan()

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

                // At this point we may or may not have a reference to the activity
                displayToast()
            }
        }
    }

}