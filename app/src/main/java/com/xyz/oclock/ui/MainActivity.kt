package com.xyz.oclock.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.OClockFirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint



enum class StartDestination(val id: Int) {
    LOGIN(R.id.loginFragment), HOME(R.id.homeFragment)
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fcm = Intent(applicationContext, OClockFirebaseMessagingService::class.java)
        startService(fcm)
        setNavigationGraph()
    }

    private fun setNavigationGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        val destinationId = intent.extras?.getInt(START_DESTIN_ID)?: return
        navGraph.setStartDestination(destinationId)
        navController.graph = navGraph
    }

    companion object {

        const val START_DESTIN_ID = "start_destination_id"

        fun startActivity(context: Context, startDestination: StartDestination) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(START_DESTIN_ID, startDestination.id)
            context.startActivity(intent)
        }
    }
}
