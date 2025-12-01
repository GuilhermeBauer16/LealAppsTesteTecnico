package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.addWorkout

data class NewWorkoutState(
    val name: String = "",
    val description: String = "",
    val isSaveButtonEnabled: Boolean = false,
    val errorMessage: String? = null,
    val isWorkoutSaved: Boolean = false
)


