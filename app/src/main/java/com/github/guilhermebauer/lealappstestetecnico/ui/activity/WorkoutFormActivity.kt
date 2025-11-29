package com.github.guilhermebauer.lealappstestetecnico.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.guilhermebauer.lealappstestetecnico.data.model.Workout
import com.github.guilhermebauer.lealappstestetecnico.databinding.ActivityWorkoutFormBinding
import com.github.guilhermebauer.lealappstestetecnico.ui.viewModel.WorkoutViewModel

class WorkoutFormActivity : AppCompatActivity() {


    private val viewModel: WorkoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        binding = ActivityWorkoutFormBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Configura botão de voltar na toolbar
//        binding.materialToolbar.setNavigationOnClickListener {
//            finish()
//        }
//
//        // Recebe workout enviado pela lista (edição)
//        workoutToEdit = intent.getParcelableExtra("workout")
//
//        workoutToEdit?.let { workout ->
//            binding.name.setText(workout.name)
//            binding.description.setText(workout.description)
//            binding.materialToolbar.title = "Edit Workout"
//            binding.buttonAddTask.text = "Update Workout"
//        }
//
//        binding.buttonAddTask.setOnClickListener {
//            val name = binding.name.text.toString()
//            val description = binding.description.text.toString()
//
//            if (name.isEmpty()) {
//                binding.name.error = "Required"
//                return@setOnClickListener
//            }
//
//            val workout = if (workoutToEdit == null) {
//                Workout(
//                    name = name,
//                    description = description
//                )
//            } else {
//                workoutToEdit!!.copy(
//                    name = name,
//                    description = description
//                )
//            }
//
//            if (workoutToEdit == null) {
//                createWorkout(workout)
//            } else {
//                updateWorkout(workout)
//            }
//        }
    }

    // Criar novo workout
    private fun createWorkout(workout: Workout) {
        viewModel.createWorkout(workout) {  msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        }
    }

    // Atualizar workout
    private fun updateWorkout(workout: Workout) {
        viewModel.updateWorkout(workout) {  msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        }
    }
}
