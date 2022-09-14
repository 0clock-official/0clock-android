package com.xyz.oclock.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentSignUpBinding
import com.xyz.oclock.common.extensions.smoothProgress
import com.xyz.oclock.ui.signup.email.SignUpEmailFragment
import com.xyz.oclock.ui.signup.nickname.SignUpNicknameFragment
import com.xyz.oclock.ui.signup.password.SignUpPasswordFragment
import com.xyz.oclock.ui.signup.stdcard.SignUpStdCardFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment :
    BindingFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up),
    SignUpViewPagerFragmentListener {

    private val viewModel: SignUpViewModel by viewModels()
    private val viewPager by lazy { binding.signUpViewpager }

    private val singUpViewsCreators: Map<Int, ()-> Fragment> = mapOf(
        0 to { SignUpEmailFragment() },
        1 to { SignUpPasswordFragment() },
        2 to { SignUpNicknameFragment() },
        3 to { SignUpStdCardFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            vm = viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setListener()
    }

    private fun setListener() {
        binding.signUpToolBar.setNavigationOnClickListener {
            if (viewPager.currentItem == 0) {
                val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                view?.findNavController()?.navigate(action)
            } else {
                viewPager.currentItem--
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.unregisterOnPageChangeCallback(viewPagerCallback)
    }

    private fun setViewPager() {
        viewPager.adapter = SingUpViewPagerAdapter(this)
        viewPager.registerOnPageChangeCallback(viewPagerCallback)
        viewPager.isUserInputEnabled = false
    }

    private val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            val percent = (((position+1).toFloat() / (singUpViewsCreators.size)) * 100).toInt()
            binding.signUpProgressbar.smoothProgress(percent)
        }
    }

    override fun moveToNextStep() {
        if (viewPager.currentItem == singUpViewsCreators.size-1) { // 마지막페이지
            // 대학생인증 서버전송
        } else {
            viewPager.currentItem++
        }
    }

    override fun setEmailOnSignUpViewModel(email: String) {
        viewModel.setEmail(email)
    }

    override fun setPasswordOnSignUpViewModel(pw: String) {
        viewModel.setPassword(pw)
    }

    override fun showLoading() {
        viewModel.isLoading = true
    }

    override fun hideLoading() {
        viewModel.isLoading = false
    }

    private inner class SingUpViewPagerAdapter(f: Fragment): FragmentStateAdapter(f) {

        override fun getItemCount(): Int = singUpViewsCreators.size

        override fun createFragment(position: Int): Fragment {
            return singUpViewsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
        }

    }
}