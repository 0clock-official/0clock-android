package com.xyz.oclock.ui.signup.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentSignUpPasswordBinding
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.common.extensions.BindingAdapter.onThrottleClick
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener

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