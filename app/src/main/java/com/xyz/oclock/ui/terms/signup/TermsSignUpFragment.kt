package com.xyz.oclock.ui.terms.signup

import android.app.AlertDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.common.extensions.onThrottleClick
import com.xyz.oclock.databinding.FragmentTermsSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsSignUpFragment: BindingFragment<FragmentTermsSignUpBinding>(R.layout.fragment_terms_sign_up) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {}.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        setRadioButtonClickListener()
        setNavigation()
        setTermsWebView()
    }

    private fun showWebView(url: String) {
        val alert = AlertDialog.Builder(context, R.style.Theme_OClock)
        val wv = WebView(this.requireContext()).apply {
            loadUrl(url)
            webViewClient = WebViewClient()
        }
        alert.setView(wv)
        alert.setOnKeyListener { dialog, keyCode, event ->
            if (event.action != KeyEvent.ACTION_DOWN)
                return@setOnKeyListener true
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (wv.canGoBack()) {
                    wv.goBack()
                } else {
                    dialog.dismiss()
                }
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        alert.setNegativeButton(R.string.confirm) { dialog, _ ->
            dialog.dismiss()
        }
        alert.show()
    }

    private fun setTermsWebView() {
        binding.layoutTerms.onThrottleClick {
            showWebView("https://www.naver.com")
        }

        binding.layoutPersonalInfo.onThrottleClick {
            showWebView("https://www.naver.com")
        }
    }

    private fun setNavigation() {
        binding.signUpTermsNextBtn.onThrottleClick {
            val action = TermsSignUpFragmentDirections.actionTermsSignUpFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        binding.signUpTermsToolbar.setNavigationOnClickListener {
            val action = TermsSignUpFragmentDirections.actionTermsSignUpFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun setRadioButtonClickListener() {
        val allAgree = binding.radioAllAgreement
        val termsAgree = binding.radioTerms
        val personalInfoAgree = binding.radioPersonalInfo

        allAgree.onThrottleClick {
            if (!allAgree.isSelected) {
                allAgree.isChecked = true
                allAgree.isSelected = true
                personalInfoAgree.isChecked = true
                personalInfoAgree.isSelected = true
                termsAgree.isChecked = true
                termsAgree.isSelected = true
                binding.signUpTermsNextBtn.isEnabled = true
            } else {
                allAgree.isChecked = false
                allAgree.isSelected = false
                personalInfoAgree.isChecked = false
                personalInfoAgree.isSelected = false
                termsAgree.isChecked = false
                termsAgree.isSelected = false
                binding.signUpTermsNextBtn.isEnabled = false
            }
        }

        termsAgree.onThrottleClick {
            if (!termsAgree.isSelected) {
                termsAgree.isChecked = true
                termsAgree.isSelected = true
            } else {
                termsAgree.isChecked = false
                termsAgree.isSelected = false
            }
            allAgree.isChecked = termsAgree.isChecked && personalInfoAgree.isChecked
            allAgree.isSelected = termsAgree.isSelected && personalInfoAgree.isSelected
            binding.signUpTermsNextBtn.isEnabled = allAgree.isChecked
        }

        personalInfoAgree.onThrottleClick {
            if (!personalInfoAgree.isSelected) {
                personalInfoAgree.isChecked = true
                personalInfoAgree.isSelected = true
            } else {
                personalInfoAgree.isChecked = false
                personalInfoAgree.isSelected = false
            }
            allAgree.isChecked = termsAgree.isChecked && personalInfoAgree.isChecked
            allAgree.isSelected = termsAgree.isSelected && personalInfoAgree.isSelected
            binding.signUpTermsNextBtn.isEnabled = allAgree.isChecked
        }
    }
}