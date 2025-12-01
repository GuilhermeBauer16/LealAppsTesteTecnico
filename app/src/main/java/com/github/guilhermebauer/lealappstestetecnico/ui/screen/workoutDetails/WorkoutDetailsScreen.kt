package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workoutDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.guilhermebauer.lealappstestetecnico.data.model.Exercise
import com.github.guilhermebauer.lealappstestetecnico.ui.theme.LealAppsTesteTecnicoTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WorkoutDetailsScreen(
    state: WorkoutDetailsState,
    onAction: (WorkoutDetailsAction) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.workout?.name ?: "Loading Workout....") },
                navigationIcon = {
                    IconButton(onClick = { onAction(WorkoutDetailsAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }

            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick =  { onAction(WorkoutDetailsAction.OnAddExerciseClick) }) {
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
                    text = "Any exercise register.\nClick '+' to add one.",
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.exercises) { exercise ->
                        ExerciseItem(exercise = exercise)
                    }
                }
            }

        }

    }

}

@Composable
fun ExerciseItem(exercise: Exercise) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = exercise.name, style = MaterialTheme.typography.titleLarge)
            exercise.observations?.let {
                if (it.isNotBlank()) {
                    Text(
                        text = "Obs: ${exercise.observations}",
                        style = MaterialTheme.typography.bodySmall
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