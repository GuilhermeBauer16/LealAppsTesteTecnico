package com.github.guilhermebauer.lealappstestetecnico.ui.screen.newExercise

import android.net.Uri

sealed interface NewExerciseAction {
    data class OnNameChange(val name: String) : NewExerciseAction
    data class OnObservationsChange(val observations: String) : NewExerciseAction
    data object SaveExercise : NewExerciseAction
    data object NavigateBack : NewExerciseAction

    data class OnImageSelected(val uri: Uri) : NewExerciseAction
}