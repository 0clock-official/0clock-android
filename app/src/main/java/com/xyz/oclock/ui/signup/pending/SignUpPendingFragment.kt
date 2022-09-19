package com.xyz.oclock.ui.signup.pending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentSignUpPendingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

enum class PendingState {
    PENDING, REJECT
}

@AndroidEntryPoint
class SignUpPendingFragment : BindingFragment<FragmentSignUpPendingBinding>(R.layout.fragment_sign_up_pending) {

    private val pendingState by lazy {
        val args: SignUpPendingFragmentArgs by navArgs()
        args.pendingState
    }

    @set:Inject
    internal lateinit var viewModelFactory: SignUpPendingViewModel.AssistedFactory

    private val viewModel: SignUpPendingViewModel by viewModels {
        SignUpPendingViewModel.provideFactory(viewModelFactory, pendingState)
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