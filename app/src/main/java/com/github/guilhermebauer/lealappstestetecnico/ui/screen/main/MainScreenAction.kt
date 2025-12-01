package com.github.guilhermebauer.lealappstestetecnico.ui.screen.main

sealed interface MainScreenAction {

    data object OnAddClick : MainScreenAction
    data class OnDeleteWorkout(val id: String) : MainScreenAction

    data class OnWorkoutClick(var workoutId: String) : MainScreenAction


}