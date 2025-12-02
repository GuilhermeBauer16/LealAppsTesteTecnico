package com.github.guilhermebauer.lealappstestetecnico.ui.screen.editExcercise

sealed interface EditExerciseAction {
    data class OnNameChange(val name: String) : EditExerciseAction
    data class OnObservationsChange(val observations: String) : EditExerciseAction
    data object UpdateExercise : EditExerciseAction
    data object NavigateBack : EditExerciseAction
}
