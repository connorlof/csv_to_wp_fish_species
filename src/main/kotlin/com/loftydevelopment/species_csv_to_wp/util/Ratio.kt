package com.loftydevelopment.species_csv_to_wp.util

data class Ratio(
    val firstValue: Int,
    val secondValue: Int,
) {
    override fun toString(): String {
        return "$firstValue:$secondValue"
    }
}