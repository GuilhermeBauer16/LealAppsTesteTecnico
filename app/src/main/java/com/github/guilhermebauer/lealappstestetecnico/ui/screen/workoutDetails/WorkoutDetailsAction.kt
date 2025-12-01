package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workoutDetails

sealed interface WorkoutDetailsAction {

    data object OnAddExerciseClick : WorkoutDetailsAction

    data class OnExerciseClick(val exerciseId: String) : WorkoutDetailsAction
    data object NavigateBack : WorkoutDetailsAction

}