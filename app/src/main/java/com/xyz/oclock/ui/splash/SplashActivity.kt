package com.xyz.oclock.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.skydoves.bindables.BindingActivity
import com.xyz.oclock.R
import com.xyz.oclock.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity: BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    companion object {
        const val SPLASH_TIME = 2000L
    }

    private val viewModel: SplashViewModel by viewModels()

    private val startTime = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            vm = viewModel
        }
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        lifecycleScope.launch {
            checkUserState()
        }
        return super.onCreateView(parent, name, context, attrs)
    }

    private suspend fun checkUserState() {
        viewModel.checkLoginState(
            onLogin = {
                checkPendingState()
            },
            onLogout = {
                moveToLoginOrOnBoarding()
            }
        )
    }

    private suspend fun checkPendingState() {
        viewModel.checkPendingState(
            onApproved = { moveToMain() },
            onRejected = { moveToPending() },
            onPending = { moveToPending() }
        )
    }
    
    private suspend fun moveToLoginOrOnBoarding() {
        waitSplashTime {
            if (viewModel.isFirstRun()) {

            } else {

            }
        }
    }
    
    private suspend fun moveToPending() {
        waitSplashTime {

        }
    }
    
    private suspend fun moveToMain() {
        waitSplashTime {
            if (viewModel.isFirstLogin()) {

            } else {

            }
        }
    }

    private suspend fun waitSplashTime(action: ()-> Unit) {
        val currentTime = System.currentTimeMillis()
        val apiSpendingTime = currentTime - startTime
        if ( apiSpendingTime > SPLASH_TIME) {
            action.invoke()
        } else {
            delay(SPLASH_TIME - apiSpendingTime)
            action.invoke()
        }
    }
}