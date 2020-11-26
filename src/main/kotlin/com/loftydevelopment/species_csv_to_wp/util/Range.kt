package com.loftydevelopment.species_csv_to_wp.util

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
        return "$start - $end"
    }
}