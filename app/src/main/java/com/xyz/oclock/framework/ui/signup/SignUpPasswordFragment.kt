package com.xyz.oclock.framework.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.oclock.R
import com.example.oclock.databinding.FragmentSignUpPasswordBinding

class SignUpPasswordFragment(private val listener: Listener): Fragment() {
    private lateinit var binding: FragmentSignUpPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_password, container, false)
        observe()
        return binding.root
    }

    private fun observe() {
        binding.signUpViewpagerPasswordNext.setOnClickListener {
            listener.onNextButtonClick()
        }
    }

    interface Listener {
        fun onNextButtonClick()
    }
}