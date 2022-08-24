package com.xyz.oclock.framework.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.key
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.oclock.R
import com.example.oclock.databinding.FragmentSignUpBinding
import com.xyz.oclock.common.smoothProgress
import com.xyz.oclock.framework.ui.login.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding
    private val viewPager by lazy { binding.signUpViewpager }

    private val singUpViewsCreators: Map<Int, ()-> Fragment> = mapOf(
        0 to { SignUpEmailFragment(object :SignUpEmailFragment.Listener {
            override fun onNextButtonClick() { viewPager.currentItem = 1 }
        })},
        1 to { SignUpPasswordFragment(object :SignUpPasswordFragment.Listener {
            override fun onNextButtonClick() { viewPager.currentItem = 2 }
        })},
        2 to { SignUpStdCardFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        setViewPager()
        observe()
        return binding.root
    }

    private fun observe() {
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

    private inner class SingUpViewPagerAdapter(f: Fragment): FragmentStateAdapter(f) {

        override fun getItemCount(): Int = singUpViewsCreators.size

        override fun createFragment(position: Int): Fragment {
            return singUpViewsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
        }

    }
}