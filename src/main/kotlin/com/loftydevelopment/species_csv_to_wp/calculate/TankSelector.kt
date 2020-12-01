package com.loftydevelopment.species_csv_to_wp.calculate

import com.loftydevelopment.species_csv_to_wp.model.Species
import com.loftydevelopment.species_csv_to_wp.model.Tank
import com.loftydevelopment.species_csv_to_wp.repository.TankRepository
import kotlin.math.abs

object TankSelector {
    fun selectTankForSpecies(species: Species, tankRepository: TankRepository): Tank? {
        val speciesTankSize = species.minTankSize
        val tanksAvailable = tankRepository.getTanks()

        return tanksAvailable
                .filter { it.size <= speciesTankSize }
                .closestValue(speciesTankSize)
    }
}

fun List<Tank>.closestValue(value: Int) = minByOrNull { abs(value - it.size) }