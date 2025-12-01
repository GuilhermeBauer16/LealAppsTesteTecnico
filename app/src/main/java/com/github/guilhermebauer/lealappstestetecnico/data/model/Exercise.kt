package com.github.guilhermebauer.lealappstestetecnico.data.model

import com.google.firebase.firestore.DocumentId

data class Exercise(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val imageUrl: String? = null,
    val observations: String? = null

)
