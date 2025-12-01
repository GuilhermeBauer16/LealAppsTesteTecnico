package com.github.guilhermebauer.lealappstestetecnico.ui.screen.workout.addWorkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.guilhermebauer.lealappstestetecnico.ui.theme.LealAppsTesteTecnicoTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NewWorkoutScreen(
    state: NewWorkoutState,
    onAction: (NewWorkoutScreenAction) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Workout") },

                navigationIcon = {
                    IconButton(onClick = { onAction(NewWorkoutScreenAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )


        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {

            OutlinedTextField(
                value = state.name,
                onValueChange = { onAction(NewWorkoutScreenAction.OnNameChange(it)) },
                label = { Text("Name") },
                placeholder = { Text("Type the name of the workout...") },
                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(
                value = state.description,
                onValueChange = { onAction(NewWorkoutScreenAction.OnDescriptionChange(it)) },
                label = { Text("Description") },
                placeholder = { Text("Type the description of the workout...") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth()


            )

            Button(
                onClick = {

                    onAction(NewWorkoutScreenAction.SaveWorkout)


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = state.isSaveButtonEnabled


            ) {
                Text("Save Workout", style = MaterialTheme.typography.titleMedium)
            }


        }

    }


}

@Composable
@Preview
private fun NewWorkoutPreview() {
    LealAppsTesteTecnicoTheme {
        NewWorkoutScreen(
            state = NewWorkoutState(),
            onAction = {}
        )
    }
}