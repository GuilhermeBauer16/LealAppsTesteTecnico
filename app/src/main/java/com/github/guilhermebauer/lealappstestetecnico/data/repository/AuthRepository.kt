package com.github.guilhermebauer.lealappstestetecnico.data.repository

import com.github.guilhermebauer.lealappstestetecnico.utils.ResultState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

) {

    suspend fun login(email: String, password: String): ResultState<String> {

        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            ResultState.Success("Login successful")
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Login failed")
        }
    }

    suspend fun register(email: String, password: String): ResultState<String> {

        return try {

            auth.createUserWithEmailAndPassword(email, password).await()
            ResultState.Success("Register successful")
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Register failed")

        }
    }

    fun userLogged() = auth.currentUser


}