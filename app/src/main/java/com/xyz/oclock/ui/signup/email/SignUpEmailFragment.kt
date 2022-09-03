package com.xyz.oclock.ui.signup.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentSignUpEmailBinding
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpEmailFragment: BindingFragment<FragmentSignUpEmailBinding>(R.layout.fragment_sign_up_email) {

    @set:Inject
    internal lateinit var viewModelFactory: SignUpEmailViewModel.AssistedFactory

    @get:VisibleForTesting
    private val viewModel: SignUpEmailViewModel by viewModels {
        SignUpEmailViewModel.provideFactory(viewModelFactory, listener)
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