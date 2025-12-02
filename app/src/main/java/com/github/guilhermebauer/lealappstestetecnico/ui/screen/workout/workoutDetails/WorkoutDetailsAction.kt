package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.workoutDetails

import com.github.guilhermebauer.lealappstestetecnico.data.model.Exercise

sealed interface WorkoutDetailsAction {

    data object OnAddExerciseClick : WorkoutDetailsAction

    data class OnExerciseClick(val exerciseId: String) : WorkoutDetailsAction
    data object NavigateBack : WorkoutDetailsAction
    data object OnDeleteWorkoutClick : WorkoutDetailsAction
    data object ConfirmDeleteWorkout : WorkoutDetailsAction

    data object DismissDeleteDialog : WorkoutDetailsAction

    data object OnEditWorkoutClick : WorkoutDetailsAction

    data class OnEditExerciseClick(val exerciseId: String) : WorkoutDetailsAction


    data class OnDeleteExerciseClick(val exercise: Exercise) : WorkoutDetailsAction

    data object ConfirmDeleteExercise : WorkoutDetailsAction

    data object DismissDeleteExerciseDialog : WorkoutDetailsAction

}