package com.xyz.oclock.framework.ui.signup.email

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.oclock.R
import com.example.oclock.databinding.FragmentSignUpEmailBinding
import com.skydoves.bindables.BindingFragment
import com.skydoves.bindables.binding
import com.xyz.oclock.common.OnThrottleClickListener
import com.xyz.oclock.common.onThrottleClick
import com.xyz.oclock.framework.ui.signup.SignUpViewPagerFragmentListener

class SignUpEmailFragment: BindingFragment<FragmentSignUpEmailBinding>(R.layout.fragment_sign_up_email) {

    private val viewModel: SignUpEmailViewModel by viewModels()
    private lateinit var listener: SignUpViewPagerFragmentListener
    private var clickable = true

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
        binding.signUpViewpagerEmailNext.onThrottleClick {
            if (viewModel.inputVerifyCode == viewModel.verifyCode) {
                setVerifyCodeLayoutStyle(isError = false)
                listener.onNextButtonClicked()
            } else {
                setVerifyCodeLayoutStyle(isError = true)
            }
        }

        binding.signUpViewpagerEmailEditText.addTextChangedListener {
            val pattern = Patterns.EMAIL_ADDRESS
            if (pattern.matcher(it.toString()).matches()) {
                binding.signUpViewpagerEmailVerifyButton.isEnabled = true
                viewModel.emailErrorHint = ""
            } else {
                viewModel.emailErrorHint = context?.getString(R.string.error_email_format)?: ""
            }
        }

        binding.signUpViewpagerVerifyEditText.addTextChangedListener {
            println("vm ${viewModel.inputVerifyCode}")
        }

        binding.signUpViewpagerEmailVerifyButton.onThrottleClick {
            viewModel.isEmailBlocked = true
            binding.signUpViewpagerEmail.setBoxBackgroundColorResource(R.color.unactivated_gray)
        }

    }

    private fun setVerifyCodeLayoutStyle(isError: Boolean) {
        if (isError) {
            binding.signUpViewpagerVerifyErrorHint.visibility = View.VISIBLE
            binding.signUpViewpagerVerifyCodeLayout.boxStrokeColor = this.requireContext().getColor(R.color.error)
        } else {
            binding.signUpViewpagerVerifyErrorHint.visibility = View.GONE
            binding.signUpViewpagerVerifyCodeLayout.boxStrokeColor = this.requireContext().getColor(R.color.purple)
        }
   }

}