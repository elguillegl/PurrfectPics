package com.elguille.purrfectpics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.elguille.purrfectpics.ui.NavGraphs
import com.elguille.purrfectpics.ui.theme.PurrfectPicsTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PurrfectPicsTheme {
                val navHostEngine = rememberNavHostEngine()

                DestinationsNavHost(
                    engine = navHostEngine,
                    navGraph = NavGraphs.root
                )
            }
        }
    }
}