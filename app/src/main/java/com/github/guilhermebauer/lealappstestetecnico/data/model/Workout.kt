package com.github.guilhermebauer.lealappstestetecnico.data.model

import com.google.firebase.Timestamp

data class Workout(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val dataset: Timestamp = Timestamp.now(),

    )
