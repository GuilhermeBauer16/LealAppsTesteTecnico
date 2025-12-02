package com.github.guilhermebauer.lealappstestetecnico.ui.screen.editExcercise

data class EditExerciseState(
    val name: String = "",
    val repetitions: String = "",
    val observations: String = "",
    val imageUrl: String? = null,
    val isLoadingInitialData: Boolean = true,
    val isUpdating: Boolean = false,
    val isExerciseUpdated: Boolean = false,
    val error: String? = null
)
