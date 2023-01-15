package com.xyz.oclock.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.skydoves.bindables.BindingActivity
import com.xyz.oclock.ui.MainActivity
import com.xyz.oclock.R
import com.xyz.oclock.databinding.ActivitySplashBinding
import com.xyz.oclock.ui.StartDestination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
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
                moveToMain()
            },
            onLogout = {
                moveToLogin()
            }
        )
    }
    
    private suspend fun moveToLogin() {
        waitSplashTime {
            MainActivity.startActivity(this, StartDestination.LOGIN)
        }
    }

    private suspend fun moveToMain() {
        waitSplashTime {
            MainActivity.startActivity(this, StartDestination.PENDING)
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