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
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.android.onetachi.R
import com.android.onetachi.databinding.UsernameFragmentBinding
import com.android.onetachi.repository.AuthRepository
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
        val view :View = inflater.inflate(R.layout.username_fragment,container,false)
        view.signupBttn.setOnClickListener {
            AuthRepository.getInstance(this.context!!).signup();
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.sending.observe(viewLifecycleOwner) { sending ->
            if (sending) {
                binding.sending.show()
            } else {
                binding.sending.hide()
            }
        }
        binding.inputId.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                viewModel.sendUsername()
                true
            } else {
                false
            }
        }




        /*viewModel.signinIntent.observe(this) { intent ->
            val a = activity
            if (intent.hasPendingIntent() && a != null) {
                try {
                    intent.launchPendingIntent(a, MainActivity.REQUEST_FIDO2_SIGNIN)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("a", "Error launching pending intent for signin request", e)
                }
            }
        }
    }*/
    }
}
