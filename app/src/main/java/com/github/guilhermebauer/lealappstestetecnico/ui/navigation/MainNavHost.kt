package com.github.guilhermebauer.lealappstestetecnico.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.main.MainScreen
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.main.MainScreenAction
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.main.MainScreenViewModel
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.newExercise.NewExerciseAction
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.newExercise.NewExerciseScreen
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.newExercise.NewExerciseViewModel
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.addWorkout.NewWorkoutScreen
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.addWorkout.NewWorkoutScreenAction
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.addWorkout.NewWorkoutViewModel
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.editWorkout.EditWorkoutAction
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.editWorkout.EditWorkoutScreen
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.editWorkout.EditWorkoutViewModel
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.workoutDetails.WorkoutDetailsAction
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.workoutDetails.WorkoutDetailsScreen
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.workoutDetails.WorkoutDetailsViewModel

@Composable
fun MainNavHost() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") {

            val viewModel = viewModel<MainScreenViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            MainScreen(state) { action ->
                when (action) {
                    is MainScreenAction.OnAddClick -> {
                        navController.navigate("addWorkout")
                    }

                    is MainScreenAction.OnWorkoutClick -> {

                        navController.navigate("workoutDetails/${action.workoutId}")
                    }


                    else -> {
                        viewModel.handleAction(action)
                    }
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

        composable(
            route = "workoutDetails/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
        ) {

            val viewModel = viewModel<WorkoutDetailsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(true) {
                viewModel.refreshData()
            }


            LaunchedEffect(state.isWorkoutDeleted) {
                if (state.isWorkoutDeleted) {
                    navController.popBackStack()
                }
            }

            WorkoutDetailsScreen(state = state) { action ->

                when (action) {

                    is WorkoutDetailsAction.NavigateBack -> {
                        navController.popBackStack()
                    }

                    is WorkoutDetailsAction.OnAddExerciseClick -> {

                        val workoutId = state.workout?.id
                        if (workoutId != null) {
                            navController.navigate("newExercise/$workoutId")
                        }

                    }

                    is WorkoutDetailsAction.OnEditWorkoutClick -> {
                        state.workout?.id?.let { navController.navigate("editWorkout/$it") }
                    }

                    is WorkoutDetailsAction.OnEditExerciseClick -> {

                        navController.navigate("editExercise/${state.workout?.id}/${action.exerciseId}")
                    }


                    else -> {
                        viewModel.onAction(action)
                    }

                }
            }


        }

        composable(
            route = "newExercise/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
        ) {

            val viewModel = viewModel<NewExerciseViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()


            LaunchedEffect(state.isExerciseSaved) {
                if (state.isExerciseSaved) {
                    navController.popBackStack()
                }
            }

            NewExerciseScreen(state = state) { action ->
                if (action is NewExerciseAction.NavigateBack) {
                    navController.popBackStack()
                } else {
                    viewModel.onAction(action)
                }
            }
        }

        composable(
            route = "editWorkout/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
        ) {
            val viewModel = viewModel<EditWorkoutViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()


            LaunchedEffect(state.isWorkoutUpdated) {
                if (state.isWorkoutUpdated) {
                    navController.popBackStack()
                }
            }


            EditWorkoutScreen(state = state) { action ->
                if (action is EditWorkoutAction.NavigateBack) {
                    navController.popBackStack()
                } else {
                    viewModel.onAction(action)
                }
            }


        }

        composable(
            route = "editExercise/{workoutId}/{exerciseId}",
            arguments = listOf(
                navArgument("workoutId") { type = NavType.StringType },
                navArgument("exerciseId") { type = NavType.StringType }
            )
        ) {

        }
    }
}

