package com.github.guilhermebauer.lealappstestetecnico.ui.screen.editExcercise


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.guilhermebauer.lealappstestetecnico.data.model.Exercise
import com.github.guilhermebauer.lealappstestetecnico.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditExerciseViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val exerciseRepository = ExerciseRepository()
    private val workoutId: String = checkNotNull(savedStateHandle["workoutId"])
    private val exerciseId: String = checkNotNull(savedStateHandle["exerciseId"])

    private val _state = MutableStateFlow(EditExerciseState())
    val state = _state.asStateFlow()

    init {
        loadExerciseData()
    }

    private fun loadExerciseData() = viewModelScope.launch {
        _state.update { it.copy(isLoadingInitialData = true) }
        val exercise = exerciseRepository.getExerciseById(workoutId, exerciseId)
        if (exercise != null) {
            _state.update {
                it.copy(
                    name = exercise.name,
                    observations = exercise.observations ?: "",
                    imageUrl = exercise.imageUrl,
                    isLoadingInitialData = false
                )
            }
        } else {
            _state.update { it.copy(error = "Exercise not found", isLoadingInitialData = false) }
        }
    }

    fun onAction(action: EditExerciseAction) {
        when (action) {
            is EditExerciseAction.OnNameChange -> _state.update { it.copy(name = action.name) }
            is EditExerciseAction.OnObservationsChange -> _state.update { it.copy(observations = action.observations) }
            is EditExerciseAction.UpdateExercise -> updateExercise()
            is EditExerciseAction.NavigateBack -> {}
            is EditExerciseAction.OnImageSelected -> {
                _state.update { it.copy(newImageUri = action.uri.toString()) }
            }
        }
    }

    private fun updateExercise() = viewModelScope.launch {
        if (_state.value.name.isBlank()) return@launch

        _state.update { it.copy(isUpdating = true) }

        val finalImageUrl: String? = if (!_state.value.newImageUri.isNullOrBlank()) {

            exerciseRepository.uploadExercisePhoto(_state.value.newImageUri!!).toString()
        } else {

            _state.value.imageUrl
        }

        val updatedExercise = Exercise(
            id = exerciseId,
            name = _state.value.name.trim(),
            observations = _state.value.observations.trim(),
            imageUrl = finalImageUrl
        )

        exerciseRepository.updateExercise(workoutId, updatedExercise)

        _state.update { it.copy(isUpdating = false, isExerciseUpdated = true) }
    }


}