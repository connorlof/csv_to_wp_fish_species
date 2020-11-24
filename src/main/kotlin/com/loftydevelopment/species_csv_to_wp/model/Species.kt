package com.loftydevelopment.species_csv_to_wp.model

import com.loftydevelopment.species_csv_to_wp.model.enum.*
import com.loftydevelopment.species_csv_to_wp.util.Quantity
import com.loftydevelopment.species_csv_to_wp.util.Range
import com.loftydevelopment.species_csv_to_wp.util.Ratio

data class Species(
        val commonName: String,
        val alternateNames: List<String>,
        val scientificName: String,
        val speciesGroup: SpeciesGroup,
        val aggroOverall: Aggressiveness,
        val aggroOwnSpecies: Aggressiveness,
        val aggroOtherSpecies: Aggressiveness,
        val idealQuantity: Quantity,
        val mfRatio: Ratio,
        val careLevel: CareLevel,
        val phRange: Range,
        val temperatureRange: Range,
        val hardnessRange: Range,
        val substrate: Substrate,
        val lightLevel: LightLevel,
        val waterCurrent: WaterCurrent,
        val isLivePlantCompatible: Boolean,
        val decorationLevel: DecorationLevel,
        val swimmingLevel: SwimmingLevel,
        val avgAdultSize: Float,
        val maxAdultSize: Float,
        val minTankSize: Int,
        val diet: Diet,
        val foodPreferences: List<FoodPreference>,
        val lifespan: Range,
        val speciesAttributes: List<String>,
)
