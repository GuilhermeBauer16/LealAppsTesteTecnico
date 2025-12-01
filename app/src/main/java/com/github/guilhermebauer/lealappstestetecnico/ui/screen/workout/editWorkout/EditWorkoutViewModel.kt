package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.editWorkout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.guilhermebauer.lealappstestetecnico.data.model.Workout
import com.github.guilhermebauer.lealappstestetecnico.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditWorkoutViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val workoutRepository = WorkoutRepository()
    private val workoutId: String = checkNotNull(savedStateHandle["workoutId"])

    private val _state = MutableStateFlow(EditWorkoutState())
    val state = _state.asStateFlow()

    init {
        loadWorkoutData()
    }

    private fun loadWorkoutData() = viewModelScope.launch {
        _state.update { it.copy(isLoadingInitialData = true) }
        val workout = workoutRepository.getWorkoutById(workoutId)
        if (workout != null) {
            _state.update {
                it.copy(
                    name = workout.name,
                    description = workout.description,
                    isLoadingInitialData = false
                )
            }
        } else {
            _state.update { it.copy(error = "Workout not found!.", isLoadingInitialData = false) }
        }
    }

    fun onAction(action: EditWorkoutAction) {

        when (action) {
            is EditWorkoutAction.OnNameChange -> _state.update { it.copy(name = action.name) }
            is EditWorkoutAction.OnDescriptionChange -> _state.update { it.copy(description = action.description) }
            is EditWorkoutAction.UpdateWorkout -> updateWorkout()
            is EditWorkoutAction.NavigateBack -> {}
        }


    }

    private fun updateWorkout() = viewModelScope.launch {
        _state.update { it.copy(isUpdating = true) }

        val updatedWorkout = Workout(
            id = workoutId,
            name = _state.value.name.trim(),
            description = _state.value.description.trim()
        )

        workoutRepository.updateWorkout(updatedWorkout)

        _state.update { it.copy(isUpdating = false, isWorkoutUpdated = true) }
    }


}