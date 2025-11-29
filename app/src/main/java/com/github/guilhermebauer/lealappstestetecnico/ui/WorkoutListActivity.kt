package com.github.guilhermebauer.lealappstestetecnico.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.guilhermebauer.lealappstestetecnico.databinding.ActivityWorkoutListBinding
import com.github.guilhermebauer.lealappstestetecnico.ui.adapter.WorkoutAdapter
import com.github.guilhermebauer.lealappstestetecnico.ui.viewModel.WorkoutViewModel
import com.github.guilhermebauer.lealappstestetecnico.utils.ResultState

class WorkoutListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutListBinding
    private val viewModel: WorkoutViewModel by viewModels()
    private lateinit var adapter: WorkoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflate do binding
        binding = ActivityWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inicializa adapter
        adapter = WorkoutAdapter { workout ->
            val intent = Intent(this, WorkoutFormActivity::class.java)
            intent.putExtra("workout", workout)
            startActivity(intent)
        }

        binding.recyclerWorkout.adapter = adapter

        // observer
        viewModel.workouts.observe(this) { state ->
            when (state) {
                is ResultState.Loading -> {
                    // loading opcional
                }

                is ResultState.Success -> {
                    adapter.submitList(state.data)
                }

                is ResultState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // buscar treinos
        viewModel.getWorkouts()

        // botão ADD
        binding.fabAddWorkout.setOnClickListener {
            startActivity(Intent(this, WorkoutFormActivity::class.java))
        }
    }
}
