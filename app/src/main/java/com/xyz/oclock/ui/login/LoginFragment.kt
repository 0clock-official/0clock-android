package com.xyz.oclock.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.common.extensions.onThrottleClick
import com.xyz.oclock.databinding.FragmentLoginBinding
import com.xyz.oclock.core.data.repository.TokenRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>( R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var tokenRepository: TokenRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            vm = viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isFirstRun()) {
            viewModel.noLongerFirstRun()
            moveToOnBoardingFragment()
            return
        }

        setOnClickListener()
        registerBackBtnCallback()
        lifecycleScope.launch {
            fcmToken()
        }
    }

    private fun moveToOnBoardingFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToOnBoardingFragment()
        view?.findNavController()?.navigate(action)
    }

    private fun setOnClickListener() {
        binding.loginDoSignUp.onThrottleClick {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        binding.loginFindPassword.onThrottleClick {
            val action = LoginFragmentDirections.actionLoginFragmentToFindPwdFragment()
            findNavController().navigate(action)
        }
    }

    private fun registerBackBtnCallback() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, callback)
    }

    private suspend fun fcmToken() {
        delay(3000)
        view?.run {
            val token = tokenRepository.getFcmToken()?: "토큰없음"
            binding.fcmSample.setText(token)
        }
    }
}