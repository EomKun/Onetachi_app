package com.android.onetachi.ui.signup

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.onetachi.MainActivity
import com.android.onetachi.R
import com.android.onetachi.databinding.SignupFragmentBinding
import com.android.onetachi.repository.AuthRepository
import com.android.onetachi.ui.home.HomeFragment
import com.android.onetachi.ui.observeOnce
import kotlinx.android.synthetic.main.signup_fragment.view.*

class SignupFragment : Fragment() {
    private val viewModel: SignupViewModel by viewModels()
    private lateinit var binding: SignupFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignupFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val view :View = inflater.inflate(R.layout.signup_fragment,container,false)
        view.sign_up_applyBtn.setOnClickListener {
            viewModel.sendSignupForm()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.signUpApplyBtn.setOnClickListener {
            viewModel.registerRequest().observeOnce(requireActivity()) { intent ->
                val a = activity
                if (intent.hasPendingIntent() && a != null) {
                    try {
                        intent.launchPendingIntent(a, MainActivity.REQUEST_FIDO2_REGISTER)
                    } catch (e: IntentSender.SendIntentException) {
                        Log.e("등록하기", "Error launching pending intent for register request", e)
                    }
                }
            }
        }*/
    }

    fun handleRegister(data: Intent) {
        viewModel.registerResponse(data)
    }

}