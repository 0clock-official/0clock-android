package com.xyz.oclock.ui.pending

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
    PENDING, REJECT, APPROVE
}

@AndroidEntryPoint
class PendingFragment : BindingFragment<FragmentSignUpPendingBinding>(R.layout.fragment_sign_up_pending) {

    companion object {
        const val ARG_PENDING_STATE = "pendingState"
    }
    private val pendingState by lazy {
        val args: PendingFragmentArgs by navArgs()
        args.pendingState
    }

    @set:Inject
    internal lateinit var viewModelFactory: PendingViewModel.AssistedFactory

    private val viewModel: PendingViewModel by viewModels {
        PendingViewModel.provideFactory(viewModelFactory, pendingState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        initView(pendingState)
        return binding {
            vm = viewModel
        }.root
    }

    private fun initView(pendingState: PendingState) {
        when(pendingState) {
            PendingState.PENDING -> {
                initPendingView()
            }
            PendingState.REJECT -> {
                initRejectView()
            }
            PendingState.APPROVE -> {
                initApproveView()
            }
        }
    }

    private fun initApproveView() {
        binding.pendingImage.setImageResource(R.drawable.img_pending_approve)
        binding.pendingTitle.text = String.format(getString(R.string.welcome), "DEMO")
        binding.pendingDesc.setText(R.string.pending_description_approve)
        binding.bottomButton.apply {
            setText(R.string.start)
            setOnClickListener {  }
        }
    }

    private fun initPendingView() {
        binding.pendingImage.setImageResource(R.drawable.img_pending_pending)
        binding.pendingTitle.setText(R.string.please_wait)
        binding.pendingDesc.setText(R.string.pending_description_pending)
        binding.bottomButton.visibility = View.GONE
    }

    private fun initRejectView() {
        binding.pendingImage.setImageResource(R.drawable.img_pending_reject)
        binding.pendingTitle.setText(R.string.please_re_certify)
        binding.pendingDesc.setText(R.string.pending_description_reject)
        binding.bottomButton.apply {
            setText(R.string.retry_verify_std_card)
            setOnClickListener {  }
        }

    }


}