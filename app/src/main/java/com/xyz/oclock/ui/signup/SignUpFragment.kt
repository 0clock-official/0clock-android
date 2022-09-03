package com.xyz.oclock.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentSignUpBinding
import com.xyz.oclock.common.extensions.smoothProgress
import com.xyz.oclock.ui.signup.email.SignUpEmailFragment
import com.xyz.oclock.ui.signup.password.SignUpPasswordFragment
import com.xyz.oclock.ui.signup.stdcard.SignUpStdCardFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(), SignUpViewPagerFragmentListener {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding
    private val viewPager by lazy { binding.signUpViewpager }

    private val singUpViewsCreators: Map<Int, ()-> Fragment> = mapOf(
        0 to { SignUpEmailFragment() },
        1 to { SignUpPasswordFragment() },
        2 to { SignUpStdCardFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        setViewPager()
        setListener()
        return binding.root
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

    override suspend fun onNextButtonClicked() {
        if (viewPager.currentItem == singUpViewsCreators.size-1) { // 마지막페이지
            // 대학생인증 서버전송
        } else {
            viewPager.currentItem++
        }
    }

    override suspend fun setEmailOnSignUpViewModel(email: String) {
        viewModel.setEmail(email)
    }

    override suspend fun setPasswordOnSignUpViewModel(pw: String) {
        viewModel.setPassword(pw)
    }

    private inner class SingUpViewPagerAdapter(f: Fragment): FragmentStateAdapter(f) {

        override fun getItemCount(): Int = singUpViewsCreators.size

        override fun createFragment(position: Int): Fragment {
            return singUpViewsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
        }

    }
}