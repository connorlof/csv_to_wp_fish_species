package com.loftydevelopment.species_csv_to_wp.calculate

object Conversions {
    fun gallonsToLiters(gallons: Double): Double {
        return gallons * 3.785
    }

    fun fahToCelsius(fah: Double): Double {
        return (fah - 32) * (5/9)
    }
}