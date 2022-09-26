package com.xyz.oclock.ui.findpwd.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import com.xyz.oclock.R
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.databinding.FragmentFindPwdEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPwdEmailFragment: BindingFragment<FragmentFindPwdEmailBinding>(R.layout.fragment_find_pwd_email) {


    @get:VisibleForTesting
    private val viewModel: FindPwdEmailViewModel by viewModels()

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