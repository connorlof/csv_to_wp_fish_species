package com.loftydevelopment.species_csv_to_wp.util

data class Ratio(
    val firstValue: Int,
    val secondValue: Int,
) {
    override fun toString(): String {
        return "$firstValue:$secondValue"
    }

    companion object {
        fun fromString(string: String): Ratio {
            val splitValues: List<String> = string.split(":")
            val firstValue = splitValues[0].trim().toInt()
            val secondValue = splitValues[1].trim().toInt()

            return Ratio(firstValue, secondValue)
        }
    }
}