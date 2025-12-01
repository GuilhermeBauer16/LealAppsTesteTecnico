package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workoutDetails

sealed interface WorkoutDetailsAction {

    data object OnAddExerciseClick : WorkoutDetailsAction

    data class OnExerciseClick(val exerciseId: String) : WorkoutDetailsAction
    data object NavigateBack : WorkoutDetailsAction
    data object OnDeleteWorkoutClick : WorkoutDetailsAction

    data object ConfirmDeleteWorkout : WorkoutDetailsAction


    data object DismissDeleteDialog : WorkoutDetailsAction

    data object OnEditWorkoutClick : WorkoutDetailsAction

}