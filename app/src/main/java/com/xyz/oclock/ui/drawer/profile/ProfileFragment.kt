package com.xyz.oclock.ui.drawer.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.core.model.ChattingTime
import com.xyz.oclock.core.model.Sex
import com.xyz.oclock.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: BindingFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()

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
        initView()
        setListener()
    }

    private fun initView() {
        val args: ProfileFragmentArgs by navArgs()
        viewModel.inputNickname = args.nickname
        viewModel.setPartnerSex(args.matchingSex - 1)
        viewModel.setPreferredTime(args.chattime - 1)
    }

    private fun setListener() {
        binding.backBtn.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
    }

}