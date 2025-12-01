package com.github.guilhermebauer.lealappstestetecnico.data.repository


import com.github.guilhermebauer.lealappstestetecnico.data.model.Exercise
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ExerciseRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
) {

    private val workoutCollection = FirebaseFirestore.getInstance().collection("workouts")


    suspend fun createExerciseToWorkout(workoutId: String, exercise: Exercise) =
        suspendCoroutine { continuation ->

            workoutCollection.document(workoutId)
                .collection("exercises")
                .add(exercise).addOnSuccessListener {
                    continuation.resume(Unit)
                }.addOnFailureListener {

                    continuation.resumeWithException(it)
                }


        }


    private fun getExerciseCollectionRef(workoutId: String) =
        db.collection("exercises").document(workoutId).collection("exercises")


    fun getExercisesForWorkout(workoutId: String): Flow<List<Exercise>> =
        callbackFlow {
            val listener = workoutCollection.document(workoutId)
                .collection("exercises")
                .orderBy("name")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val exercises = snapshot.toObjects(Exercise::class.java)
                        trySend(exercises).isSuccess
                    }
                }

            awaitClose { listener.remove() }
        }


    suspend fun deleteExercise(workoutId: String, exerciseId: String) {
        try {
            workoutCollection.document(workoutId)
                .collection("exercises")
                .document(exerciseId)
                .delete()
                .await()
        } catch (e: Exception) {
            println("Error to delete exercise: ${e.message}")
        }
    }
}