package com.github.guilhermebauer.lealappstestetecnico.ui.workout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.guilhermebauer.lealappstestetecnico.data.model.Workout
import com.github.guilhermebauer.lealappstestetecnico.data.repository.WorkoutRepository
import com.github.guilhermebauer.lealappstestetecnico.utils.ResultState
import kotlinx.coroutines.launch

class WorkoutViewModel : ViewModel() {

    private val workoutRepository = WorkoutRepository()

    val workouts = MutableLiveData<ResultState<List<Workout>>>()
    val result = MutableLiveData<ResultState<String>>()

    fun loadWorkouts(){
        workouts.value = ResultState.Loading()
        viewModelScope.launch {
            workouts.value = workoutRepository.listWorkout()

        }

    }

    fun createWorkout(workout: Workout){
        result.value = ResultState.Loading()
        viewModelScope.launch {
            result.value = workoutRepository.createWorkout(workout)

        }
    }

    fun deleteWorkout(id: String){
        result.value = ResultState.Loading()
        viewModelScope.launch {
            result.value = workoutRepository.deleteTraining(id)
        }

    }




}