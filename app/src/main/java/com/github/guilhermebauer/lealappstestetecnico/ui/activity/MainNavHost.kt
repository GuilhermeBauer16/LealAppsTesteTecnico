package com.github.guilhermebauer.lealappstestetecnico.ui.activity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.addWorkout.NewWorkoutScreen
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.addWorkout.NewWorkoutScreenAction
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.addWorkout.NewWorkoutViewModel
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.main.MainScreen
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.main.MainScreenAction
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.main.MainScreenViewModel

@Composable
fun MainNavHost() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") {

            val viewModel = viewModel<MainScreenViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            MainScreen(state) {
                if (it is MainScreenAction.OnAddClick) {

                    navController.navigate("addWorkout")

                } else {
                    viewModel.handleAction(action = it)
                }
            }


        }

        composable("addWorkout") {

            val viewModel = viewModel<NewWorkoutViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(state.isWorkoutSaved) {
                if (state.isWorkoutSaved) {
                    navController.popBackStack()
                }

            }

            NewWorkoutScreen(state = state) { action ->
                if (action is NewWorkoutScreenAction.NavigateBack) {

                    navController.popBackStack()
                } else {
                    viewModel.onAction(action)
                }
            }
        }


    }
}