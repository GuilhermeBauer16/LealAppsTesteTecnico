package com.github.guilhermebauer.lealappstestetecnico.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Workout(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val dataset: Long = System.currentTimeMillis(),

    ) : Parcelable
