package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.workoutDetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.guilhermebauer.lealappstestetecnico.data.repository.ExerciseRepository
import com.github.guilhermebauer.lealappstestetecnico.data.repository.WorkoutRepository
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.editWorkout.EditWorkoutScreen
import com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.editWorkout.EditWorkoutState
import com.github.guilhermebauer.lealappstestetecnico.ui.theme.LealAppsTesteTecnicoTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutDetailsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {


    private val workoutRepository = WorkoutRepository()

    private val exerciseRepository = ExerciseRepository()

    private val workoutId: String = checkNotNull(savedStateHandle["workoutId"])

    private val _state = MutableStateFlow(WorkoutDetailsState())
    val state = _state.asStateFlow()

    init {
        getExercises()
        getWorkoutDetails()
    }

    fun refreshData() {
        getWorkoutDetails()
        getExercises()
    }

    fun onAction(action: WorkoutDetailsAction) = viewModelScope.launch {
        when (action) {

            is WorkoutDetailsAction.OnAddExerciseClick -> {}
            is WorkoutDetailsAction.NavigateBack -> {}
            is WorkoutDetailsAction.OnExerciseClick -> {}
            is WorkoutDetailsAction.OnDeleteWorkoutClick -> {
                _state.update { it.copy(isConfirmDeleteDialogVisible = true) }
                workoutRepository.deleteWorkout(workoutId)

            }

            is WorkoutDetailsAction.DismissDeleteDialog -> {
                _state.update { it.copy(isConfirmDeleteDialogVisible = false) }
            }


            is WorkoutDetailsAction.ConfirmDeleteWorkout -> {
                _state.update { it.copy(isConfirmDeleteDialogVisible = false) }
                workoutRepository.deleteWorkout(workoutId)
                _state.update { it.copy(isWorkoutDeleted = true) }

            }

            is WorkoutDetailsAction.OnEditExerciseClick -> {
                // A navegação será tratada no NavHost. Ação vazia aqui.
            }

            is WorkoutDetailsAction.OnDeleteExerciseClick -> {
                _state.update {
                    it.copy(
                        isConfirmDeleteExerciseDialogVisible = true,
                        exerciseToDelete = action.exercise // Guarda qual exercício deletar
                    )
                }
            }

            is WorkoutDetailsAction.DismissDeleteExerciseDialog -> {
                _state.update {
                    it.copy(
                        isConfirmDeleteExerciseDialogVisible = false,
                        exerciseToDelete = null // Limpa o exercício guardado
                    )
                }
            }

            is WorkoutDetailsAction.ConfirmDeleteExercise -> {
                viewModelScope.launch {
                    val exerciseIdToDelete = _state.value.exerciseToDelete?.id
                    if (exerciseIdToDelete != null) {
                        exerciseRepository.deleteExercise(workoutId = workoutId, exerciseId = exerciseIdToDelete)
                    }

                    _state.update {
                        it.copy(
                            isConfirmDeleteExerciseDialogVisible = false,
                            exerciseToDelete = null
                        )
                    }
                }
            }

            is WorkoutDetailsAction.OnEditWorkoutClick -> TODO()
        }
    }


    private fun getWorkoutDetails() = viewModelScope.launch {
        val workout = workoutRepository.getWorkoutById(workoutId)
        _state.update { it.copy(workout = workout) }
    }

    private fun getExercises() = viewModelScope.launch {
        workoutRepository.getExercisesForWorkout(workoutId)
            .collectLatest { exercises ->

            _state.update {
                it.copy(isLoading = false, exercises = exercises)
            }
        }
    }


}





