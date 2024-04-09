package com.elguille.purrfectpics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.elguille.purrfectpics.ui.cats.CatsViewModel
import com.elguille.purrfectpics.ui.cats.NavGraphs
import com.elguille.purrfectpics.ui.theme.PurrfectPicsTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PurrfectPicsTheme {

                val enterFromRightTransition = remember { slideInHorizontally { it } }
                val exitToLeftTransition = remember { slideOutHorizontally { -it } }
                val enterFromLeftTransition = remember { slideInHorizontally { -it } }
                val exitToRightTransition = remember { slideOutHorizontally { it } }
                val navHostEngine =
                    rememberAnimatedNavHostEngine(
                        navHostContentAlignment = Alignment.TopCenter,
                        rootDefaultAnimations =
                        RootNavGraphDefaultAnimations(
                            enterTransition = { enterFromRightTransition },
                            exitTransition = { exitToLeftTransition },
                            popEnterTransition = { enterFromLeftTransition },
                            popExitTransition = { exitToRightTransition })
                    )

                DestinationsNavHost(
                    engine = navHostEngine,
                    navGraph = NavGraphs.root,
                    modifier = Modifier.fillMaxSize(),
                    dependenciesContainerBuilder = {
                        dependency(NavGraphs.root) {
                            val parentEntry =
                                remember(navBackStackEntry) {
                                    navController.getBackStackEntry(NavGraphs.root.route)
                                }

                            val catsViewModel = hiltViewModel<CatsViewModel>(parentEntry)
                            dependency(catsViewModel)
                        }
                    })
            }
        }
    }
}