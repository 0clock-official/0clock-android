package com.xyz.oclock.ui.signup.pending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentSignUpPendingBinding
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import javax.inject.Inject

class SignUpPendingFragment : BindingFragment<FragmentSignUpPendingBinding>(R.layout.fragment_sign_up_pending) {

    @set:Inject
    internal lateinit var viewModelFactory: SignUpPendingViewModel.AssistedFactory

    @get:VisibleForTesting
    private val viewModel: SignUpPendingViewModel by viewModels {
        SignUpPendingViewModel.provideFactory(viewModelFactory, listener)
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