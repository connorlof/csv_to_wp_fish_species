package com.loftydevelopment.species_csv_to_wp.calculate

import com.loftydevelopment.species_csv_to_wp.model.Heater
import com.loftydevelopment.species_csv_to_wp.model.Species
import com.loftydevelopment.species_csv_to_wp.repository.HeaterRepository
import kotlin.math.abs

object HeaterSelector {
    fun selectHeaterForSpecies(species: Species, heaterRepository: HeaterRepository): Heater? {
        val speciesTankSize = species.minTankSize
        val heatersAvailable = heaterRepository.getHeaters()

        // Handle special case for species < 10g
        if (speciesTankSize < 10) return heatersAvailable.closestValue(speciesTankSize)

        return heatersAvailable
                .filter { it.ratedGallons.isWithinRange(speciesTankSize.toDouble()) }
                .closestValue(speciesTankSize)
    }
}

fun List<Heater>.closestValue(value: Int) = minByOrNull { abs(value - it.ratedGallons.end) }