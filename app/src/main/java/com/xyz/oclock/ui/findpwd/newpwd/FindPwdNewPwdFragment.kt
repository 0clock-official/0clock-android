package com.xyz.oclock.ui.findpwd.newpwd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.xyz.oclock.R
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.common.extensions.onThrottleClick
import com.xyz.oclock.databinding.FragmentFindPwdNewPwdBinding
import com.xyz.oclock.ui.findpwd.FindPwdFragment
import dagger.hilt.android.AndroidEntryPoint

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.findPwdNewPwdViewpagerPasswordNext.onThrottleClick {
            (parentFragment as FindPwdFragment).onClickNextButton()
        }
    }

}