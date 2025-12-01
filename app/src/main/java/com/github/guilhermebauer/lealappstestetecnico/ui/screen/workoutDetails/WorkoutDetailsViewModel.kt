package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workoutDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.guilhermebauer.lealappstestetecnico.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutDetailsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {


    private val workoutRepository = WorkoutRepository()

    private val workoutId: String = checkNotNull(savedStateHandle["workoutId"])

    private val _state = MutableStateFlow(WorkoutDetailsState())
    val state = _state.asStateFlow()

    init {
        getExercises()
        getWorkoutDetails()
    }


    private fun getWorkoutDetails() = viewModelScope.launch {
        val workout = workoutRepository.getWorkoutById(workoutId)
        _state.update { it.copy(workout = workout) }
    }

    private fun getExercises() = viewModelScope.launch {
        workoutRepository.getExercisesForWorkout(workoutId).collectLatest { exercises ->
            _state.update { it.copy(isLoading = false, exercises = exercises) }
        }
    }


}




