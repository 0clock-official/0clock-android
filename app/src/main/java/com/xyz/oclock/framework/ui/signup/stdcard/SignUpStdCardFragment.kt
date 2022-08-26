package com.xyz.oclock.framework.ui.signup.stdcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentSignUpStdCardBinding

class SignUpStdCardFragment: Fragment() {

    private lateinit var binding: FragmentSignUpStdCardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_std_card, container, false)
        return binding.root
    }
}