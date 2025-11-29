package com.github.guilhermebauer.lealappstestetecnico.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.guilhermebauer.lealappstestetecnico.databinding.ActivityLoginBinding
import com.github.guilhermebauer.lealappstestetecnico.ui.workout.WorkoutViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }


        }

    }

    private fun login(email: String, password: String) {
        binding.btnLogin.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                startActivity(Intent(this, WorkoutViewModel::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error to login", Toast.LENGTH_SHORT).show()
                binding.btnLogin.isEnabled = true
                binding.progressBar.visibility = View.GONE
            }


    }


}