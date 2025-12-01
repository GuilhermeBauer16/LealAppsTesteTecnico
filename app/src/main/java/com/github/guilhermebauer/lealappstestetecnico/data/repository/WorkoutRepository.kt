package com.github.guilhermebauer.lealappstestetecnico.data.repository

import com.github.guilhermebauer.lealappstestetecnico.data.model.Exercise
import com.github.guilhermebauer.lealappstestetecnico.data.model.Workout
import com.github.guilhermebauer.lealappstestetecnico.utils.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class WorkoutRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val workoutRef = db.collection("workouts")

//    suspend fun createWorkout(workout: Workout): ResultState<String> {
//        return try {
//            val doc = workoutRef.document()
//            val newWorkout = workout.copy(id = doc.id)
//            doc.set(newWorkout).await()
//            ResultState.Success("Workout Created")
//        } catch (e: Exception) {
//            ResultState.Error(e.message ?: "Error to create workout")
//        }
//    }

    suspend fun createWorkout(workout: Workout) = suspendCoroutine { continuation ->

        val doc = workoutRef.document()
        val newWorkout = workout.copy(id = doc.id)
        doc.set(newWorkout).addOnSuccessListener {
            continuation.resume(Unit)
        }.addOnFailureListener {

            continuation.resumeWithException(it)
        }


    }

    suspend fun getExercisesForWorkout(workoutId: String): Flow<List<Exercise>> = callbackFlow {
        val exercisesCollection = workoutRef.document(workoutId).collection("exercises")

        val snapshotListener = exercisesCollection.orderBy("name")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val exercises = snapshot.toObjects(Exercise::class.java)
                    trySend(exercises).isSuccess
                }
            }
        awaitClose { snapshotListener.remove() }
    }

    suspend fun getWorkoutById(workoutId: String): Workout? {
        return try {
            workoutRef.document(workoutId).get().await().toObject(Workout::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateWorkout(workout: Workout): ResultState<String> {
        return try {
            workoutRef.document(workout.id).set(workout).await()
            ResultState.Success("Workout updated")
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Error to update workout")
        }
    }

    suspend fun deleteWorkout(id: String) = suspendCoroutine { continuation ->

        workoutRef.document(id).delete().addOnSuccessListener {
            continuation.resume(Unit)
        }.addOnFailureListener {

            continuation.resumeWithException(it)
        }


    }


    fun getWorkouts(): Flow<List<Workout>> = callbackFlow {

        val snapshotListener = workoutRef.orderBy("name", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->

                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val workouts = snapshot.toObjects(Workout::class.java)

                    trySend(workouts).isSuccess
                }
            }

        awaitClose {
            snapshotListener.remove()
        }


    }
}