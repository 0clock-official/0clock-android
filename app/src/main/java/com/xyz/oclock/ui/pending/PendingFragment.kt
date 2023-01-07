package com.xyz.oclock.ui.pending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentSignUpPendingBinding
import com.xyz.oclock.ui.dialog.DefaultDialog
import com.xyz.oclock.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PendingFragment : BindingFragment<FragmentSignUpPendingBinding>(R.layout.fragment_sign_up_pending) {

    private val viewModel: PendingViewModel by viewModels()

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
        checkPendingState()
    }

    private fun checkPendingState() {
        viewModel.checkPendingState(
            onApproved = {
                if (viewModel.isFirstLogin()) {
                    viewModel.noLongerFirstLogin()
                    initApproveView()
                } else {
                    moveToHome()
                }
            },
            onRejected = {
                initRejectView()
            },
            onPending = {
                initPendingView()
            },
            onError = {
                DefaultDialog.build(this.requireContext()) {
                    message = it
                    singleButtonText = getString(R.string.confirm)
                    singleButtonListener = View.OnClickListener() {
                        moveToLogin()
                    }
                }.show()
            }
        )
    }

    private fun initApproveView() {
        binding.pendingImage.setImageResource(R.drawable.img_pending_approve)
        binding.pendingTitle.text = String.format(getString(R.string.welcome), "DEMO")
        binding.pendingDesc.setText(R.string.pending_description_approve)
        binding.bottomButton.apply {
            setText(R.string.start)
            setOnClickListener { moveToHome() }
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
            setOnClickListener {
                moveToUploadStdCard()
            }
        }
    }

    private fun moveToUploadStdCard() {
        val action = PendingFragmentDirections.actionPendingFragmentToUploadStdCardFragment()
        view?.findNavController()?.navigate(action)
    }

    private fun moveToHome() {
        val action = PendingFragmentDirections.actionPendingFragmentToHomeFragment()
        view?.findNavController()?.navigate(action)
    }

    private fun moveToLogin() {
        val action = PendingFragmentDirections.actionPendingFragmentToLoginFragment()
        view?.findNavController()?.navigate(action)
    }

}