package com.loftydevelopment.species_csv_to_wp.model

import com.loftydevelopment.species_csv_to_wp.model.enum.Diet
import com.loftydevelopment.species_csv_to_wp.model.enum.FoodPreference
import com.loftydevelopment.species_csv_to_wp.model.enum.SpeciesGroup
import com.loftydevelopment.species_csv_to_wp.util.Range

data class Food(
        val fishSize: Range,
        val dietType: Diet,
        val foodType: FoodPreference,
        val speciesGroups: List<SpeciesGroup>,
        val isSpeciesSpecific: Boolean,
        val name: String,
        val imageUrl: String,
        val productUrl: String,
)
