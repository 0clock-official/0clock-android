package com.xyz.oclock.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentHomeBinding
import com.xyz.oclock.ui.pending.PendingState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
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
                    moveToPending(PendingState.APPROVE)
                }
            },
            onRejected = {
                moveToPending(PendingState.REJECT)
             },
            onPending = {
                moveToPending(PendingState.PENDING)
            }
        )
    }

    private fun moveToPending(pendingState: PendingState) {
        val action = HomeFragmentDirections.actionHomeFragmentToPendingFragment(pendingState)
        view?.findNavController()?.navigate(action)
    }
}