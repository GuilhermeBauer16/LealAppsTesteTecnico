package com.github.guilhermebauer.lealappstestetecnico.ui.screen.main

import com.github.guilhermebauer.lealappstestetecnico.data.model.Workout

data class MainScreenState(
    val workoutList: List<Workout> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
