/*
 * Copyright 2019 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.onetachi.ui.username

import android.R.attr.button
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.android.onetachi.MainActivity
import com.android.onetachi.R
import com.android.onetachi.databinding.UsernameFragmentBinding
import com.android.onetachi.repository.AuthRepository
import com.android.onetachi.ui.observeOnce
import kotlinx.android.synthetic.main.username_fragment.*
import kotlinx.android.synthetic.main.username_fragment.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class UsernameFragment : Fragment() {

    private val viewModel: UsernameViewModel by viewModels()
    private lateinit var binding: UsernameFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UsernameFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.sending.observe(viewLifecycleOwner) { sending ->
            if (sending) {
                binding.sending.show()
            } else {
                binding.sending.hide()
            }
        }

        binding.signupBtn.setOnClickListener {
            viewModel.signup()
        }

        // Signin Listener
        binding.next.setOnClickListener {
            viewModel.signin().observeOnce(this) { intent ->
                val a = activity
                if (intent.hasPendingIntent() && a != null) {
                    try {
                        intent.launchPendingIntent(a, MainActivity.REQUEST_FIDO2_SIGNIN)
                    } catch (e: IntentSender.SendIntentException) {
                        Log.e("Signin", "Error launching pending intent for signin request", e)
                    }
                }
            }
            // viewModel.signin()
        }
    }

    fun handleSignin(data: Intent) {
        viewModel.signinResponse(data)
        viewModel.completeSignin()
    }
}
