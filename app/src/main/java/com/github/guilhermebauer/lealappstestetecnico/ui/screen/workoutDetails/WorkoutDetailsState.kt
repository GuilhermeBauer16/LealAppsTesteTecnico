package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workoutDetails

import com.github.guilhermebauer.lealappstestetecnico.data.model.Exercise
import com.github.guilhermebauer.lealappstestetecnico.data.model.Workout

data class WorkoutDetailsState(
    val isLoading: Boolean = true,
    val workout: Workout? = null,
    val exercises: List<Exercise> = emptyList(),
    val error: String? = null,
    val isConfirmDeleteDialogVisible: Boolean = false,
    val isWorkoutDeleted: Boolean = false

)
