package com.xyz.oclock.framework.ui.signup.pending

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xyz.oclock.R

class SignUpPendingFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpPendingFragment()
    }

    private lateinit var viewModel: SignUpPendingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up_pending, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpPendingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}