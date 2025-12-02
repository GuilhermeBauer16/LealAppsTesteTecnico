package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.workoutDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.guilhermebauer.lealappstestetecnico.R
import com.github.guilhermebauer.lealappstestetecnico.data.model.Exercise
import com.github.guilhermebauer.lealappstestetecnico.ui.theme.LealAppsTesteTecnicoTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WorkoutDetailsScreen(
    state: WorkoutDetailsState,
    onAction: (WorkoutDetailsAction) -> Unit
) {

    if (state.isConfirmDeleteExerciseDialogVisible) {
        AlertDialog(
            onDismissRequest = { onAction(WorkoutDetailsAction.DismissDeleteExerciseDialog) },
            title = { Text(stringResource(R.string.delete_exercise)) },
            text = { Text(
                stringResource(
                    R.string.you_are_sure_you_want_to_delete_the_exercise,
                    state.exerciseToDelete?.name ?: ""
                )) },
            confirmButton = {
                Button(
                    onClick = { onAction(WorkoutDetailsAction
                        .ConfirmDeleteExercise) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                Button(onClick = { onAction(WorkoutDetailsAction.DismissDeleteExerciseDialog) }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    if (state.isConfirmDeleteDialogVisible) {
        AlertDialog(
            onDismissRequest = {

                onAction(WorkoutDetailsAction.DismissDeleteDialog)
            },
            title = {
                Text(text = stringResource(R.string.delete_workout))
            },
            text = {
                Text(stringResource(R.
                string
                    .you_are_sure_you_want_to_delete_this_workout_all_the_exercises_will_be_unavailable))
            },
            confirmButton = {
                Button(
                    onClick = {

                        onAction(WorkoutDetailsAction.ConfirmDeleteWorkout)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                Button(
                    onClick = {

                        onAction(WorkoutDetailsAction.DismissDeleteDialog)
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.workout?.name ?: stringResource(R.string.loading_workout)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(WorkoutDetailsAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                },
                actions = {

                    IconButton(onClick = { onAction(WorkoutDetailsAction.OnEditWorkoutClick) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Workout"
                        )
                    }


                    IconButton(onClick = { onAction(WorkoutDetailsAction.OnDeleteWorkoutClick) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Workout",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }


            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(WorkoutDetailsAction.OnAddExerciseClick) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Exercise"
                )
            }

        }

    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        )
        {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.exercises.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_exercise_register_click_to_add_one),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.exercises, key= {it.id}) { exercise ->
                        ExerciseItem(
                            exercise = exercise,
                            onAction = onAction
                        )
                    }
                }
            }

        }

    }

}


@Composable
fun ExerciseItem(
    exercise: Exercise,
    onAction: (WorkoutDetailsAction) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = exercise.imageUrl,
                contentDescription = "Exercise Image",
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop

            )
            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)) {
                Text(text = exercise.name, style = MaterialTheme.typography.titleMedium)

                if (exercise.observations?.isNotBlank() == true) {
                    Text(
                        text = "Obs: ${exercise.observations}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }


            Box {
                IconButton(onClick = { isMenuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Options")
                }

                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = {
                            isMenuExpanded = false
                            onAction(WorkoutDetailsAction.OnEditExerciseClick(exercise.id))
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            isMenuExpanded = false
                            onAction(WorkoutDetailsAction.OnDeleteExerciseClick(exercise))
                        }
                    )
                }
            }
        }
    }
}


@Composable
@Preview
private fun WorkoutDetailsScreenPreview() {
    LealAppsTesteTecnicoTheme {
        WorkoutDetailsScreen(
            state = WorkoutDetailsState(),
            onAction = {}
        )
    }
}