package com.github.guilhermebauer.lealappstestetecnico.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
                            "Leal Apps" ,
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

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)){





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