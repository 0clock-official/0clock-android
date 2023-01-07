package com.xyz.oclock.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.navigation.NavigationView
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.common.extensions.BindingAdapter.visibility
import com.xyz.oclock.databinding.FragmentHomeBinding
import com.xyz.oclock.ui.dialog.DefaultDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment:
    BindingFragment<FragmentHomeBinding>(R.layout.fragment_home)
{

    private val viewModel: HomeViewModel by viewModels()

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
        setListener()
        startMatching()
    }

    private fun startMatching() {
        viewModel.startMatching(
            onSuccess = {
                DefaultDialog.build(this.requireContext()) {
                    message = getString(R.string.matching_success)
                    singleButtonText = getString(R.string.confirm)
                    singleButtonListener = View.OnClickListener() {
                        getMatchingUserInfo()
                    }
                }.show()
            },
            onFail = {
                showNoMatchingScreen()
                DefaultDialog.build(this.requireContext()) {
                    message = getString(R.string.matching_fail)
                    singleButtonText = getString(R.string.confirm)
                }.show()
            },
            onAlreadyMatched = {
                getMatchingUserInfo()
            }
        )
    }

    private fun showNoMatchingScreen() {
        binding.waitScreen.visibility = View.VISIBLE
        binding.backBtn.visibility = View.GONE
        binding.title.text = ""
    }

    private fun getMatchingUserInfo() {
        viewModel.getMatchingUserInfo(
            onSuccess = {

            },
            onFail = {

            }
        )
    }

    private fun setListener() {
        binding.menu.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.RIGHT)
        }
        binding.navigationView.setNavigationItemSelectedListener { it: MenuItem ->
            when (it.itemId) {
                R.id.menu_edit_profile -> {
                    viewModel.showToast("edit_profile")
                    true
                }
                R.id.menu_setting -> {
                    viewModel.showToast("setting")
                    true
                }
                else -> {
                    true
                }
            }
        }
        val headerView = binding.navigationView.getHeaderView(0)
        val navigationCloseBtn = headerView.findViewById<ImageView>(R.id.drawer_close_btn)
        navigationCloseBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(Gravity.END)
        }
    }
}