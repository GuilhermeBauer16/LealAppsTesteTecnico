package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.editWorkout

data class EditWorkoutState(
    val name: String = "",
    val description: String = "",
    val isLoadingInitialData: Boolean = true,
    val isUpdating: Boolean = false,
    val isWorkoutUpdated: Boolean = false,
    val error: String? = null

)
