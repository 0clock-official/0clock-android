package com.xyz.oclock.framework.ui.signup.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.oclock.R
import com.example.oclock.databinding.FragmentSignUpPasswordBinding
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.common.onThrottleClick
import com.xyz.oclock.framework.ui.signup.SignUpViewPagerFragmentListener

class SignUpPasswordFragment: BindingFragment<FragmentSignUpPasswordBinding>(R.layout.fragment_sign_up_password) {

    private val viewModel: SignUpPasswordViewModel by viewModels()
    private lateinit var listener: SignUpViewPagerFragmentListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        listener = parentFragment as SignUpViewPagerFragmentListener
        setListener()
        return binding {
            vm = viewModel
        }.root
    }

    private fun setListener() {
        binding.signUpViewpagerPasswordNext.onThrottleClick {
            listener.onNextButtonClicked()
        }
    }
}