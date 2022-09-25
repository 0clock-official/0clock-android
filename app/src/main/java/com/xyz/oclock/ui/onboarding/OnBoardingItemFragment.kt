package com.xyz.oclock.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.databinding.FragmentOnBoardingItemBinding

class OnBoardingItemFragment: BindingFragment<FragmentOnBoardingItemBinding>(R.layout.fragment_on_boarding_item) {

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
        val page = arguments?.getInt(PAGE)
        page?.run {
            initView(this)
        }
    }

    private fun initView(page: Int) {
        when (page) {
            0 -> {
                binding.onBoardingImage.setImageResource(R.drawable.img_on_boarding_1)
                binding.onBoardingDescription.setText(R.string.on_boarding_description1)
            }
            1 -> {
                binding.onBoardingImage.setImageResource(R.drawable.img_on_boarding_3)
                binding.onBoardingDescription.setText(R.string.on_boarding_description2)
            }
            2 -> {
                binding.onBoardingImage.setImageResource(R.drawable.img_on_boarding_3)
                binding.onBoardingDescription.setText(R.string.on_boarding_description3)
            }
        }
    }

    companion object {
        const val PAGE = "PAGE"

        fun newInstance(page: Int): OnBoardingItemFragment {
            val fragment = OnBoardingItemFragment()
            val bundle = Bundle()
            bundle.putInt(PAGE, page)
            fragment.arguments = bundle
            return fragment
        }
    }
}