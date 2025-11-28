package com.github.guilhermebauer.lealappstestetecnico.data.repository

import com.github.guilhermebauer.lealappstestetecnico.data.model.Exercise
import com.github.guilhermebauer.lealappstestetecnico.utils.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class ExerciseRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
) {

    suspend fun createExercise(workoutId: String, exercise: Exercise): ResultState<String> {

        return try {
            val ref = db.collection("exercises")
                .document(workoutId)
                .collection("exercises")
                .document()
            val newExercise = exercise.copy(id = ref.id)
            ref.set(newExercise).await()

            ResultState.Success("Exercise created")
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Error to create exercise")
        }

    }

    suspend fun listExercises(workoutId: String): ResultState<List<Exercise>> {

        return try {
            val data = db.collection("exercises")
                .document(workoutId)
                .collection("exercises")
                .get()
                .await()
                .toObjects(Exercise::class.java)


            ResultState.Success(data)
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Error to list exercises")
        }

    }
}