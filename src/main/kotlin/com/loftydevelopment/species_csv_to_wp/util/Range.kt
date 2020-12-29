package com.loftydevelopment.species_csv_to_wp.util

import com.loftydevelopment.species_csv_to_wp.calculate.Conversions
import kotlin.math.roundToInt

data class Range(
    val start: Double,
    val end: Double,
) {
    init {
        require(start <= end) { "start must be less than or equal to end" }
    }

    fun isWithinRange(value: Double): Boolean {
        return value in start..end
    }

    override fun toString(): String {
        var string = "${start.roundToInt()} - ${end.roundToInt()}"

        if (start == end) string = start.roundToInt().toString()

        return string
    }

    fun toStringDecimal(places: Int): String {
        var string = "${String.format("%.${places}f", start)} - ${String.format("%.${places}f", end)}"

        if (start == end) string = String.format("%.${places}f", start)

        return string
    }

    fun convertFahToCelsius(): Range {
        val startAsCelsius = Conversions.fahToCelsius(start)
        val endAsCelsius = Conversions.fahToCelsius(end)

        return Range(startAsCelsius, endAsCelsius)
    }

    companion object {
        fun fromString(string: String): Range {
            val splitValues: List<String> = string.split("-")
            val start = splitValues[0].trim().toDouble()
            val end = splitValues[1].trim().toDouble()

            return Range(start, end)
        }
    }
}