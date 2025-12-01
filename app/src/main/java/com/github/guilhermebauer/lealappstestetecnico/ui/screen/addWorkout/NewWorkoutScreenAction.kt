package com.github.guilhermebauer.lealappstestetecnico.ui.screen.addWorkout

sealed interface NewWorkoutScreenAction {

    data class OnNameChange(val name: String) : NewWorkoutScreenAction
    data class OnDescriptionChange(val description: String) : NewWorkoutScreenAction
    object SaveWorkout : NewWorkoutScreenAction

    data object NavigateBack : NewWorkoutScreenAction
}