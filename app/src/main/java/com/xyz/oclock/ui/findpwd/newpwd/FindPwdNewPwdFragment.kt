package com.xyz.oclock.ui.findpwd.newpwd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentSignUpPasswordBinding
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.databinding.FragmentFindPwdNewPwdBinding
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FindPwdNewPwdFragment: BindingFragment<FragmentFindPwdNewPwdBinding>(R.layout.fragment_find_pwd_new_pwd) {

    private val viewModel: FindPwdNewPwdViewModel by viewModels()
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