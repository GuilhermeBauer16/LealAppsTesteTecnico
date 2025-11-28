package com.github.guilhermebauer.lealappstestetecnico.data.repository

import com.github.guilhermebauer.lealappstestetecnico.data.model.Workout
import com.github.guilhermebauer.lealappstestetecnico.utils.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class WorkoutRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val workoutRef = db.collection("workouts")

    suspend fun createWorkout(workout: Workout): ResultState<String> {
        return try {
            val doc = workoutRef.document()
            val newWorkout = workout.copy(id = doc.id)
            doc.set(workout).await()
            ResultState.Success("Workout Created")
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Error to create workout")
        }
    }

    suspend fun listTraining(): ResultState<List<Workout>> {

        return try {

            val data = workoutRef.get().await().toObjects(Workout::class.java)
            ResultState.Success(data)

        } catch (e: Exception) {

            ResultState.Error(e.message ?: "Error to list workouts")

        }


    }

    suspend fun deleteTraining(id: String): ResultState<String> {
        return try {
            workoutRef.document(id).delete().await()
            ResultState.Success("Workout deleted")


        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Error to delete workout")
        }
    }
}
