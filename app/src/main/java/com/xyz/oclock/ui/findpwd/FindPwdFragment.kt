package com.xyz.oclock.ui.findpwd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.common.extensions.BindingAdapter.smoothProgress
import com.xyz.oclock.databinding.FragmentFindPwdBinding
import com.xyz.oclock.ui.findpwd.email.FindPwdEmailFragment
import com.xyz.oclock.ui.findpwd.newpwd.FindPwdNewPwdFragment

class FindPwdFragment: BindingFragment<FragmentFindPwdBinding>(R.layout.fragment_find_pwd), FindPwdListener {

    private val viewPager by lazy { binding.findPwdViewpager }

    private val findPwdViewsCreators: Map<Int, () -> Fragment> = mapOf(
        0 to { FindPwdEmailFragment() },
        1 to { FindPwdNewPwdFragment() }
    )

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
        setViewPager()
        setListener()
    }

    private fun setListener() {
        binding.findPwdToolBar.setNavigationOnClickListener {
            if (viewPager.currentItem == 0) {
                moveToLogin()
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
        viewPager.adapter = FindPwdViewPagerAdapter(this)
        viewPager.registerOnPageChangeCallback(viewPagerCallback)
        viewPager.isUserInputEnabled = false
    }

    private val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            val percent = (((position + 1).toFloat() / (findPwdViewsCreators.size)) * 100).toInt()
            binding.findPwdProgressbar.smoothProgress(percent)
        }
    }

    private fun moveToLogin() {
        val action = FindPwdFragmentDirections.actionFindPwdFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    override fun onClickNextButton() {
        if (viewPager.currentItem == findPwdViewsCreators.size-1) {
            moveToLogin()
        } else {
            viewPager.currentItem++
        }
    }

    private inner class FindPwdViewPagerAdapter(f: Fragment): FragmentStateAdapter(f) {

        override fun getItemCount(): Int = findPwdViewsCreators.size

        override fun createFragment(position: Int): Fragment {
            return findPwdViewsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
        }

    }
}