package com.github.guilhermebauer.lealappstestetecnico.ui.screen.editExcercise

import android.net.Uri

sealed interface EditExerciseAction {

    data class OnNameChange(val name: String) : EditExerciseAction
    data class OnObservationsChange(val observations: String) : EditExerciseAction
    data object UpdateExercise : EditExerciseAction
    data object NavigateBack : EditExerciseAction
    data class OnImageSelected(val uri: Uri) : EditExerciseAction
}
