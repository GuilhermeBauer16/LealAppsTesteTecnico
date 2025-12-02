package com.github.guilhermebauer.lealappstestetecnico.ui.screen.newExercise

import android.net.Uri

data class NewExerciseState(
    val name: String = "",
    val imageUri: String? = null,
    val observations: String = "",
    val isLoading: Boolean = false,
    val isExerciseSaved: Boolean = false,
    val error: String? = null

)
