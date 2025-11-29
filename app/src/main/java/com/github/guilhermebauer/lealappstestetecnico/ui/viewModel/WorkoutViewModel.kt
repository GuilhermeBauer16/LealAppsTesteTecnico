package com.github.guilhermebauer.lealappstestetecnico.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.guilhermebauer.lealappstestetecnico.data.model.Workout
import com.github.guilhermebauer.lealappstestetecnico.data.repository.WorkoutRepository
import com.github.guilhermebauer.lealappstestetecnico.utils.ResultState
import kotlinx.coroutines.launch

class WorkoutViewModel(private val repository: WorkoutRepository = WorkoutRepository()) :
    ViewModel() {

    private val _workouts = MutableLiveData<ResultState<List<Workout>>>()
    val workouts: LiveData<ResultState<List<Workout>>> = _workouts

    fun getWorkouts() {
        viewModelScope.launch {
            _workouts.value = ResultState.Loading()
            _workouts.value = repository.listWorkout()

        }

    }

    fun createWorkout(workout: Workout, onFinish: (String) -> Unit) {
        viewModelScope.launch {
            when (val result = repository.createWorkout(workout)) {
                is ResultState.Success -> onFinish(result.data)
                is ResultState.Error -> onFinish(result.message)

                else -> {}
            }
        }
    }

    fun updateWorkout(workout: Workout, onFinish: (String) -> Unit) {
        viewModelScope.launch {
            when (val result = repository.updateWorkout(workout)) {
                is ResultState.Success -> onFinish(result.data)
                is ResultState.Error -> onFinish(result.message)

                else -> {}
            }
        }
    }

    fun deleteWorkout(id: String, onFinish: (String) -> Unit) {
        viewModelScope.launch {
            when (val result = repository.deleteTraining(id)) {
                is ResultState.Success -> onFinish(result.data)
                is ResultState.Error -> onFinish(result.message)
                else -> {}

            }
        }
    }


}