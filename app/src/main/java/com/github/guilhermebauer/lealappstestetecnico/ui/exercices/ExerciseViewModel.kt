//package com.github.guilhermebauer.lealappstestetecnico.ui.exercices
//
//import android.net.Uri
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.github.guilhermebauer.lealappstestetecnico.data.model.Exercise
//import com.github.guilhermebauer.lealappstestetecnico.data.repository.ExerciseRepository
//import com.github.guilhermebauer.lealappstestetecnico.presentation.utils.ExerciseUiState
//import com.github.guilhermebauer.lealappstestetecnico.utils.ResultState // Seu ResultState
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class ExerciseViewModel(
//    private val repository: ExerciseRepository
//) : ViewModel() {
//
//
//    private val _uiState = MutableStateFlow<Exercise>(e.Idle)
//    val uiState: StateFlow<ExerciseUiState> = _uiState.asStateFlow()
//
//    // Estado para a lista de exercícios
//    private val _exerciseList = MutableStateFlow<List<Exercise>>(emptyList())
//    val exerciseList: StateFlow<List<Exercise>> = _exerciseList.asStateFlow()
//
//-
//    fun createExercise(workoutId: String, exercise: Exercise, imageUri: Uri?) {
//        _uiState.value = ExerciseUiState.Loading
//        viewModelScope.launch {
//            when (val result = repository.createExercise(workoutId, exercise, imageUri)) {
//                is ResultState.Success -> {
//                    _uiState.value = ExerciseUiState.Success(result.data)
//                    // Opcional: Recarregar lista após criação
//                    listExercises(workoutId)
//                }
//                is ResultState.Error -> {
//                    _uiState.value = ExerciseUiState.Error(result.data)
//                }
//            }
//        }
//    }
//
//    // --- CRUD: READ/LIST ---
//    fun listExercises(workoutId: String) {
//        _uiState.value = ExerciseUiState.Loading
//        viewModelScope.launch {
//            when (val result = repository.listExercises(workoutId)) {
//                is ResultState.Success -> {
//                    _exerciseList.value = result.data
//                    _uiState.value = ExerciseUiState.ListSuccess("Lista carregada")
//                }
//                is ResultState.Error -> {
//                    _uiState.value = ExerciseUiState.Error(result.data)
//                }
//            }
//        }
//    }
//
//    // --- CRUD: UPDATE ---
//    fun updateExercise(workoutId: String, exercise: Exercise, imageUri: Uri?) {
//        _uiState.value = ExerciseUiState.Loading
//        viewModelScope.launch {
//            when (val result = repository.updateExercise(workoutId, exercise, imageUri)) {
//                is ResultState.Success -> {
//                    _uiState.value = ExerciseUiState.Success(result.data)
//                    listExercises(workoutId) // Recarrega para mostrar a atualização
//                }
//                is ResultState.Error -> {
//                    _uiState.value = ExerciseUiState.Error(result.data)
//                }
//            }
//        }
//    }
//
//    // --- CRUD: DELETE ---
//    fun deleteExercise(workoutId: String, exerciseId: String) {
//        _uiState.value = ExerciseUiState.Loading
//        viewModelScope.launch {
//            when (val result = repository.deleteExercise(workoutId, exerciseId)) {
//                is ResultState.Success -> {
//                    _uiState.value = ExerciseUiState.Success(result.data)
//                    listExercises(workoutId) // Recarrega para refletir a exclusão
//                }
//                is ResultState.Error -> {
//                    _uiState.value = ExerciseUiState.Error(result.data)
//                }
//            }
//        }
//    }
//
//    // Método para redefinir o estado da UI (útil após exibir mensagens de sucesso/erro)
//    fun resetUiState() {
//        _uiState.value = ExerciseUiState.Idle
//    }
//}