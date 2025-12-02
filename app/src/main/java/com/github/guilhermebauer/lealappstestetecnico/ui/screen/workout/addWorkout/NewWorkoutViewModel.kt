package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.addWorkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.guilhermebauer.lealappstestetecnico.R
import com.github.guilhermebauer.lealappstestetecnico.data.model.Workout
import com.github.guilhermebauer.lealappstestetecnico.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewWorkoutViewModel() : ViewModel() {

    private val workoutRepository = WorkoutRepository()

    private val _state = MutableStateFlow(NewWorkoutState())
    val state = _state.asStateFlow()

    fun onAction(action: NewWorkoutScreenAction) = viewModelScope.launch {

        when (action) {
            is NewWorkoutScreenAction.OnNameChange -> {

                _state.update { currentState ->
                    currentState.copy(name = action.name)

                }
                updateButtonState()
                
            }

            is NewWorkoutScreenAction.OnDescriptionChange -> {
                _state.update { currentState ->
                    currentState.copy(description = action.description)
                }

            }

            NewWorkoutScreenAction.SaveWorkout -> {
                    saveWorkout()

            }


            is NewWorkoutScreenAction.NavigateBack -> {

            }
        }


    }


    private fun saveWorkout() = viewModelScope.launch {

        val currentName = state.value.name
        val currentDescription = state.value.description

        if (currentName.isBlank()) {

            _state.update { it.copy(errorMessage = "Name cannot be empty") }

            return@launch

        }

        val workoutToSave = Workout(
            name = currentName,
            description = currentDescription
        )

        try {
            workoutRepository.createWorkout(workoutToSave)
            _state.update { currentState ->
                currentState.copy(isWorkoutSaved = true)
            }

        } catch (e: Exception) {

            _state.update { it.copy(errorMessage = e.message) }

            e.printStackTrace()


        }


    }

    private fun updateButtonState() {
        val isEnable = state.value.name.isNotBlank()

        _state.update { currentState ->
            currentState.copy(isSaveButtonEnabled = isEnable)
        }
    }

}
