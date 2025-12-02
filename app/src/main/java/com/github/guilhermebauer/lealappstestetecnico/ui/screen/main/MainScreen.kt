package com.github.guilhermebauer.lealappstestetecnico.ui.screen.main

import com.github.guilhermebauer.lealappstestetecnico.R
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.guilhermebauer.lealappstestetecnico.data.model.Workout
import com.github.guilhermebauer.lealappstestetecnico.ui.theme.LealAppsTesteTecnicoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun MainScreen(
    state: MainScreenState,
    onAction: (MainScreenAction) -> Unit

) {

    Scaffold(
        topBar =
            {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.leal_apps),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,

                            )
                    }
                )
            },

        floatingActionButton = {
            FloatingActionButton(

                onClick = { onAction(MainScreenAction.OnAddClick) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new workout"
                )
            }
        }
    )
    { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.workoutList.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_workout_found_click_in_the_button_to_add_one),
                    textAlign = TextAlign.Center
                )
            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement
                        .spacedBy(16.dp)
                ) {
                    items(state.workoutList, key = { it.id }) { workout ->
                        WorkoutItem(workout = workout, onAction = onAction)
                    }
                }
            }
        }

    }


}

@Composable
fun WorkoutItem(workout: Workout, onAction: (MainScreenAction) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAction(MainScreenAction
                .OnWorkoutClick(workout.id)) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = workout.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = workout.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
@Preview
private fun MainScreenPreview() {
    LealAppsTesteTecnicoTheme {
        MainScreen(
            state = MainScreenState(),
            onAction = {}
        )
    }
}