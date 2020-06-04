package com.android.onetachi.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.onetachi.databinding.ResultsuccessFragmentBinding

class ResultFragment: Fragment() {
    private val viewModel: ResultViewModel by viewModels()
    private lateinit var binding: ResultsuccessFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ResultsuccessFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.OKBtn.setOnClickListener {
            viewModel.OKStatus()
        }
    }
}