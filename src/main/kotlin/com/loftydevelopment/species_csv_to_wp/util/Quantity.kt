package com.loftydevelopment.species_csv_to_wp.util

data class Quantity(
        val isInfinite: Boolean,
        val isSingleValueOrGreater: Boolean,
        val values: List<Int>,
) {
    init {
        if (values.isEmpty()) {
            require(isInfinite) { "isInfinite must be set if no values are set" }
        }

        if (isSingleValueOrGreater) {
            require(values.size == 1) { "Only a single value may be included if isSingleValueOrGreater" }
        }
    }

    fun isWithinRange(value: Double): Boolean {
        if (isInfinite) return true

        if (isSingleValueOrGreater) {
            return isEqualOrGreater(value)
        }

        if (containsMatchingValue(value)) {
            return true
        }

        return false
    }

    private fun isEqualOrGreater(value: Double): Boolean {
        if (values.size > 1 || values.isEmpty()) return false

        if (value >= values[0]) {
            return true
        }

        return false
    }

    private fun containsMatchingValue(value: Double): Boolean {
        return values.map { it.toDouble() }.contains(value)
    }

    override fun toString(): String {
        if (isInfinite) return "Any"
        if (isSingleValueOrGreater) return "${values[0]}+"
        if (values.size == 1) return "${values[0]}"

        var output = "${values[0]}"

        for (index in 1 until values.size) {
            output = "${output}, ${values[index]}"
        }

        return output
    }

    companion object {
        fun fromString(string: String): Quantity {
            val isInfinite = string.trim() == "any"
            if (isInfinite) return Quantity(isInfinite, false, listOf())

            val isSingleValueOrGreater = string.contains("+")

            val values: MutableList<Int> = mutableListOf()
            val stringValues = string.split(",")
            values.addAll(stringValues.map { it.filter { c: Char ->  c.isDigit() }.trim().toInt() })

            return Quantity(isInfinite, isSingleValueOrGreater, values)
        }
    }
}