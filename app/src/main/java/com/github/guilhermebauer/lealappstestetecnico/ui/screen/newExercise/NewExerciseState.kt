package com.github.guilhermebauer.lealappstestetecnico.ui.screen.newExercise

data class NewExerciseState(
    val name: String = "",
    val observations: String = "",
    val isLoading: Boolean = false,
    val isExerciseSaved: Boolean = false,
    val error: String? = null

)
