package com.elguille.purrfectpics.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.elguille.purrfectpics.ui.cats.CatsViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.dependency

@OptIn(ExperimentalMaterialNavigationApi::class,
    ExperimentalAnimationApi::class
)
@RootNavGraph(start = true)
@Destination
@Composable
fun PurrfectPicsAppScreen() {
    val enterFromRightTransition = remember { slideInHorizontally { it } }
    val exitToLeftTransition = remember { slideOutHorizontally { -it } }
    val enterFromLeftTransition = remember { slideInHorizontally { -it } }
    val exitToRightTransition = remember { slideOutHorizontally { it } }

    val navHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations =
        RootNavGraphDefaultAnimations(
            enterTransition = { enterFromRightTransition },
            exitTransition = { exitToLeftTransition },
            popEnterTransition = { enterFromLeftTransition },
            popExitTransition = { exitToRightTransition }),
        defaultAnimationsForNestedNavGraph = mapOf(
            NavGraphs.root to NestedNavGraphDefaultAnimations(
                enterTransition = { enterFromRightTransition },
                exitTransition = { exitToLeftTransition },
                popEnterTransition = { enterFromLeftTransition },
                popExitTransition = { exitToRightTransition }
            )
        )
    )
    val navController = navHostEngine.rememberNavController()

    DestinationsNavHost(
        engine = navHostEngine,
        navController = navController,
        navGraph = NavGraphs.content,
        modifier = Modifier
            .fillMaxSize(),
        dependenciesContainerBuilder = {
            dependency(NavGraphs.content) {
                val parentEntry =
                    remember(navBackStackEntry) {
                        navController.getBackStackEntry(NavGraphs.content.route)
                    }

                val catsViewModel = hiltViewModel<CatsViewModel>(parentEntry)
                dependency(catsViewModel)
            }
        }
    )
}