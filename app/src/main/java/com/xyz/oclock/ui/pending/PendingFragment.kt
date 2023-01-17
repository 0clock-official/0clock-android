package com.xyz.oclock.ui.pending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.skydoves.bindables.BindingFragment
import com.xyz.oclock.R
import com.xyz.oclock.core.model.ChattingTime
import com.xyz.oclock.databinding.FragmentSignUpPendingBinding
import com.xyz.oclock.ui.dialog.DefaultDialog
import com.xyz.oclock.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PendingFragment : BindingFragment<FragmentSignUpPendingBinding>(R.layout.fragment_sign_up_pending) {

    private val viewModel: PendingViewModel by viewModels()

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
        checkPendingState()
        getMyInfo()
        setListener()
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

    private fun checkPendingState() {
        viewModel.checkPendingState(
            onApproved = {
                if (viewModel.isFirstLogin()) {
                    viewModel.noLongerFirstLogin()
                    initApproveView()
                } else {
                    moveToHome()
                }
            },
            onRejected = {
                initRejectView()
            },
            onPending = {
                initPendingView()
            },
            onError = {
                lifecycleScope.launch {
                    DefaultDialog.build(this@PendingFragment.requireContext()) {
                        message = it
                        singleButtonText = getString(R.string.confirm)
                        singleButtonListener = View.OnClickListener() {
                            moveToLogin()
                        }
                    }.show()
                }
            }
        )
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

    private fun initApproveView() {
        binding.pendingImage.setImageResource(R.drawable.img_pending_approve)
        binding.pendingTitle.text = String.format(getString(R.string.welcome), viewModel.nickname)
        binding.pendingDesc.setText(R.string.pending_description_approve)
        binding.bottomButton.apply {
            setText(R.string.start)
            setOnClickListener { moveToHome() }
        }
    }

    private fun initPendingView() {
        binding.pendingImage.setImageResource(R.drawable.img_pending_pending)
        binding.pendingTitle.setText(R.string.please_wait)
        binding.pendingDesc.setText(R.string.pending_description_pending)
        binding.bottomButton.visibility = View.GONE
    }

    private fun initRejectView() {
        binding.pendingImage.setImageResource(R.drawable.img_pending_reject)
        binding.pendingTitle.setText(R.string.please_re_certify)
        binding.pendingDesc.setText(R.string.pending_description_reject)
        binding.bottomButton.apply {
            setText(R.string.retry_verify_std_card)
            setOnClickListener {
                moveToUploadStdCard()
            }
        }
    }

    private fun moveToUploadStdCard() {
        val action = PendingFragmentDirections.actionPendingFragmentToUploadStdCardFragment()
        view?.findNavController()?.navigate(action)
    }

    private fun moveToHome() {
        val action = PendingFragmentDirections.actionPendingFragmentToHomeFragment()
        view?.findNavController()?.navigate(action)
    }

    private fun moveToLogin() {
        val action = PendingFragmentDirections.actionPendingFragmentToLoginFragment()
        view?.findNavController()?.navigate(action)
    }

}