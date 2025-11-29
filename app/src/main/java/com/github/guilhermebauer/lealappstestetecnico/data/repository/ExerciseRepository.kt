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

    private fun getExerciseCollectionRef(workoutId: String) =
        db.collection("exercises").document(workoutId).collection("exercises")



    suspend fun updateExercise(workoutId: String, exercise: Exercise): ResultState<String> {
        return try {
            if (exercise.id.isEmpty()) {
                return ResultState.Error("Exercise ID cannot be empty for update")
            }
            getExerciseCollectionRef(workoutId).document(exercise.id).set(exercise).await()
            ResultState.Success("Exercise updated")
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Error to update exercise")
        }
    }

    suspend fun deleteExercise(workoutId: String, exerciseId: String): ResultState<String> {
        return try {
            getExerciseCollectionRef(workoutId).document(exerciseId).delete().await()
            ResultState.Success("Exercise deleted")
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Error to delete exercise")
        }
    }
}