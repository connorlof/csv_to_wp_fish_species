package com.loftydevelopment.species_csv_to_wp.model

import com.loftydevelopment.species_csv_to_wp.util.Range

data class Heater(
        val ratedGallons: Range,
        val name: String,
        val imageUrl: String,
        val productUrl: String
)
