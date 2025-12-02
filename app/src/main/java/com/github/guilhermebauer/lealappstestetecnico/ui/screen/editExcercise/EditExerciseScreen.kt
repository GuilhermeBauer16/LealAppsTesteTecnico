package com.github.guilhermebauer.lealappstestetecnico.ui.screen.editExcercise

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.guilhermebauer.lealappstestetecnico.R
import com.github.guilhermebauer.lealappstestetecnico.ui.theme.LealAppsTesteTecnicoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExerciseScreen(
    state: EditExerciseState,
    onAction: (EditExerciseAction) -> Unit
) {


    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { onAction(EditExerciseAction.OnImageSelected(it)) }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit_exercise)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(EditExerciseAction.NavigateBack) }) {
                        Icon(Icons
                            .AutoMirrored
                            .Filled
                            .ArrowBack,
                            contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (state.isLoadingInitialData) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                val imageToShow = state.newImageUri ?: state.imageUrl

                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable {

                            imagePicker.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageToShow.isNullOrBlank()) {

                        Icon(
                            painter = painterResource(R.drawable.image_24dp),
                            contentDescription = "Add photo",
                            modifier = Modifier.size(120.dp),
                            tint = Color.Black
                        )
                    } else {

                        AsyncImage(
                            model = imageToShow,
                            contentDescription = "Exercise image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }


                OutlinedTextField(
                    value = state.name,
                    onValueChange = { onAction(EditExerciseAction
                        .OnNameChange(it)) },
                    label = { Text(stringResource(R.string.name)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.observations,
                    onValueChange = { onAction(EditExerciseAction
                        .OnObservationsChange(it)) },
                    label = { Text(stringResource(R.string.observation_optional)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onAction(EditExerciseAction.UpdateExercise) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.name.isNotBlank() && !state.isUpdating
                ) {
                    if (state.isUpdating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(stringResource(R.string.update_information))
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun EditExercisePreview() {
    LealAppsTesteTecnicoTheme {
        EditExerciseScreen(
            state = EditExerciseState(),
            onAction = {}
        )
    }
}
