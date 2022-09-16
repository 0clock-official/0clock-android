package com.xyz.oclock.ui.signup.time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentSignUpTimeBinding
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpTimeFragment: BindingFragment<FragmentSignUpTimeBinding>(R.layout.fragment_sign_up_time) {

    @set:Inject
    internal lateinit var viewModelFactory: SignUpTimeViewModel.AssistedFactory

    @get:VisibleForTesting
    private val viewModel: SignUpTimeViewModel by viewModels {
        SignUpTimeViewModel.provideFactory(viewModelFactory, listener)
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