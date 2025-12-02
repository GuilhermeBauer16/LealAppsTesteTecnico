package com.github.guilhermebauer.lealappstestetecnico.ui.screen.newExercise

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.guilhermebauer.lealappstestetecnico.R
import com.github.guilhermebauer.lealappstestetecnico.ui.theme.LealAppsTesteTecnicoTheme


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NewExerciseScreen(
    state: NewExerciseState,
    onAction: (NewExerciseAction) -> Unit
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            onAction(NewExerciseAction.OnImageSelected(uri))

        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.new_exercise)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(NewExerciseAction.NavigateBack) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment
                .CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(16.dp)
        ) {

            if (state.imageUri == null) {

                Icon(
                    painter = painterResource(R.drawable.image_24dp),
                    modifier = Modifier
                        .size(120.dp)
                        .clickable {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts
                                        .PickVisualMedia
                                        .ImageOnly
                                )
                            )
                        },
                    contentDescription = "Exercise Image",
                    tint = Color.Black
                )


            } else {

                AsyncImage(
                    model = state.imageUri,
                    contentDescription = "Exercise Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clickable {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                    contentScale = ContentScale.Crop

                )

            }
            OutlinedTextField(
                value = state.name,
                onValueChange = { onAction(NewExerciseAction
                    .OnNameChange(it)) },
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.observations,
                onValueChange = { onAction(NewExerciseAction
                    .OnObservationsChange(it)) },
                label = { Text(stringResource(R.string.observations_optional)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onAction(NewExerciseAction
                    .SaveExercise) },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.name.isNotBlank() && !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(stringResource(R.string.save_exercise))
                }
            }
        }
    }
}

@Composable
@Preview
private fun NewWorkoutPreview() {
    LealAppsTesteTecnicoTheme {
        NewExerciseScreen(
            state = NewExerciseState(),
            onAction = {}
        )
    }
}