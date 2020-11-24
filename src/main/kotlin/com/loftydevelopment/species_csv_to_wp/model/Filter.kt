package com.loftydevelopment.species_csv_to_wp.model

import com.loftydevelopment.species_csv_to_wp.model.enum.FilterType

data class Filter(
        val size: Int,
        val type: FilterType,
        val name: String,
        val imageUrl: String,
        val productUrl: String,
)