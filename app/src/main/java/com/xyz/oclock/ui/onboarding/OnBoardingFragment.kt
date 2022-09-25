package com.xyz.oclock.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.common.extensions.onThrottleClick
import com.xyz.oclock.databinding.FragmentOnBoardingBinding

class OnBoardingFragment: BindingFragment<FragmentOnBoardingBinding>(R.layout.fragment_on_boarding) {

    private val viewPager by lazy { binding.onBoardingViewpager }

    private val onBoardingViewsCreators: Map<Int, ()-> Fragment> = mapOf(
        0 to { OnBoardingItemFragment.newInstance(0) },
        1 to { OnBoardingItemFragment.newInstance(1) },
        2 to { OnBoardingItemFragment.newInstance(2) }
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
        binding.onBoardingNext.onThrottleClick {
            if (viewPager.currentItem == onBoardingViewsCreators.size -1) {
                val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment()
                view?.findNavController()?.navigate(action)
            } else {
                viewPager.currentItem++
            }
        }
    }

    private fun setViewPager() {
        viewPager.adapter = OnBoardingViewPagerAdapter(this)
        TabLayoutMediator(binding.onBoardingTap, viewPager) { tab, _ ->
            viewPager.currentItem = tab.position
        }.attach()
    }

    private inner class OnBoardingViewPagerAdapter(f: Fragment): FragmentStateAdapter(f) {

        override fun getItemCount(): Int = onBoardingViewsCreators.size

        override fun createFragment(position: Int): Fragment {
            return onBoardingViewsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
        }

    }
}