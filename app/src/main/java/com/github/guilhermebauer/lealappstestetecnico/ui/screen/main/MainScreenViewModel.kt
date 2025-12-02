package com.github.guilhermebauer.lealappstestetecnico.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.guilhermebauer.lealappstestetecnico.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {

    private val workoutRepository = WorkoutRepository()


    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    init {
        getWorkouts()
    }

    fun handleAction(action: MainScreenAction) = viewModelScope.launch {

        when (action) {
            is MainScreenAction.OnDeleteWorkout -> {

            }

            else -> {}
        }
    }


    private fun getWorkouts() {
        viewModelScope.launch {

            workoutRepository.getWorkouts().collectLatest { workouts ->
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        workoutList = workouts
                    )
                }
            }
        }
    }

}