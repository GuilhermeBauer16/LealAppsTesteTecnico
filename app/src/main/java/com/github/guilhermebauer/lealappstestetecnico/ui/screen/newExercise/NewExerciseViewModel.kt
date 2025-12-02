package com.github.guilhermebauer.lealappstestetecnico.ui.screen.newExercise

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.guilhermebauer.lealappstestetecnico.data.model.Exercise
import com.github.guilhermebauer.lealappstestetecnico.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class NewExerciseViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val exerciseRepository = ExerciseRepository()
    private val workoutId: String = checkNotNull(savedStateHandle["workoutId"])

    private val _state = MutableStateFlow(NewExerciseState())
    val state = _state.asStateFlow()


    fun onAction(action: NewExerciseAction) = viewModelScope.launch {
        when (action) {

            is NewExerciseAction.OnNameChange -> _state.update { it.copy(name = action.name) }
            is NewExerciseAction.OnObservationsChange -> _state.update { it.copy(observations = action.observations) }
            is NewExerciseAction.SaveExercise -> saveExercise()
            is NewExerciseAction.NavigateBack -> {}

            is NewExerciseAction.OnImageSelected -> {
                _state.update {
                    it.copy(imageUri = action.uri.toString())

                }

            }
        }


    }


    private fun saveExercise() = viewModelScope.launch {

        _state.update { it.copy(isLoading = true) }

        val imageUrl: String? = if (!_state.value.imageUri.isNullOrBlank()) {

            exerciseRepository.uploadExercisePhoto(_state.value.imageUri!!).toString()
        } else {

            null
        }

        val exercise = Exercise(
            name = _state.value.name,
            observations = _state.value.observations,
            imageUrl = imageUrl
        )

        exerciseRepository.createExerciseToWorkout(workoutId, exercise)
        _state.update { it.copy(isLoading = false, isExerciseSaved = true) }


    }

}