package com.github.guilhermebauer.lealappstestetecnico.data.repository


import androidx.core.net.toUri
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
import kotlin.text.get

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


    suspend fun updateExercise(workoutId: String, exercise: Exercise) =
        suspendCoroutine { continuation ->

            workoutCollection.document(workoutId)
                .collection("exercises")
                .document(exercise.id)
                .set(exercise)
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }.addOnFailureListener {

                    continuation.resumeWithException(it)
                }

        }

    suspend fun uploadExercisePhoto(imgPath: String) = suspendCoroutine { continuation ->
        storage.getReference("images").child("image_${System.currentTimeMillis()}")
            .putFile(imgPath.toUri())
            .addOnSuccessListener { task ->
                task.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                    continuation.resume(it)
                }
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

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

    suspend fun getExerciseCollectionRef(workoutId: String) = {
        db.collection("exercises")
            .document(workoutId).collection("exercises")

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

    suspend fun getExerciseById(workoutId: String, exerciseId: String) =
        suspendCoroutine { continuation ->


                workoutCollection.document(workoutId)
                    .collection("exercises")
                    .document(exerciseId)
                    .get()
                    .addOnSuccessListener {
                        continuation
                            .resume(it.toObject(Exercise::class.java))

                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }




        }
}

