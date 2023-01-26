package com.xyz.oclock.ui.home

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.core.model.ChattingTime
import com.xyz.oclock.databinding.FragmentHomeBinding
import com.xyz.oclock.ui.dialog.DefaultDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            adapter = ChatAdapter()
            vm = viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        getServerTime()
        getMyInfo()
        startMatching()
    }

    private fun getServerTime() {
        viewModel.getServerTime()
    }

    private fun getMyInfo() {
        viewModel.getMyInfo(
            onSuccess = { user ->
                setDrawerHeaderView(
                    nickname = user.nickname,
                    profileImg = 0,
                    chatTime = ChattingTime.values().filter { it.index == user.chattingTime }[0].desc,
                    major = "경영학과"
                )
            },
            onGetDate = {
            },
            onFail = {
                setDrawerHeaderView(
                    nickname = "",
                    profileImg = 0,
                    chatTime = "데이터를 불러오는 데 실패했습니다.",
                    major = ""
                )
            }
        )
    }

    private fun setDrawerHeaderView(
        nickname: String,
        profileImg: Int,
        chatTime: String,
        major: String
    ) {
        lifecycleScope.launch {
            val headerView = binding.navigationView.getHeaderView(0)
            headerView.findViewById<ImageView>(R.id.drawer_profile_img).apply { }
            headerView.findViewById<TextView>(R.id.drawer_nickname).apply { text = nickname }
            headerView.findViewById<TextView>(R.id.drawer_chat_time).apply { text = chatTime }
            headerView.findViewById<TextView>(R.id.drawer_major).apply { text = major }
        }
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
                lifecycleScope.launch {
                    showNoMatchingScreen()
                    DefaultDialog.build(this@HomeFragment.requireContext()) {
                        message = getString(R.string.matching_fail)
                        singleButtonText = getString(R.string.confirm)
                    }.show()
                }
            },
            onAlreadyMatched = {
                getMatchingUserInfo()
            }
        )
    }

    private fun showNoMatchingScreen() {
        binding.waitScreen.visibility = View.VISIBLE
        binding.title.text = ""
    }

    private fun getMatchingUserInfo() {
        viewModel.getMatchingUserInfo(
            onSuccess = {
                binding.title.text = it.nickname
                viewModel.subscribeToSocketEvents()
                viewModel.sendMessage()

            },
            onFail = {
                binding.title.text = "알 수 없음"
            }
        )
    }

    private fun setListener() {
        binding.menu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)
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
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }
    }
}