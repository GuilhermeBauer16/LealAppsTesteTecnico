package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.editWorkout

sealed interface EditWorkoutAction {
    data class OnNameChange(val name: String) : EditWorkoutAction
    data class OnDescriptionChange(val description: String) : EditWorkoutAction
    data object UpdateWorkout : EditWorkoutAction
    data object NavigateBack : EditWorkoutAction
}
