package com.xyz.oclock.ui.signup.nickname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentSignUpNicknameBinding
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpNicknameFragment: BindingFragment<FragmentSignUpNicknameBinding>(R.layout.fragment_sign_up_nickname) {

    @set:Inject
    internal lateinit var viewModelFactory: SignUpNickNameViewModel.AssistedFactory

    @get:VisibleForTesting
    private val viewModel: SignUpNickNameViewModel by viewModels {
        SignUpNickNameViewModel.provideFactory(viewModelFactory, listener)
    }
    private val listener: SignUpViewPagerFragmentListener by lazy {
        parentFragment as SignUpViewPagerFragmentListener
    }

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

}