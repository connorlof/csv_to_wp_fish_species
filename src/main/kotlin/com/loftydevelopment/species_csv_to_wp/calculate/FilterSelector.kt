package com.loftydevelopment.species_csv_to_wp.calculate

import com.loftydevelopment.species_csv_to_wp.model.Filter
import com.loftydevelopment.species_csv_to_wp.model.Species
import com.loftydevelopment.species_csv_to_wp.repository.FilterRepository
import kotlin.math.abs

object FilterSelector {
    fun selectFilterForSpecies(species: Species, filterRepository: FilterRepository): Filter? {
        val speciesTankSize = species.minTankSize
        val filtersAvailable = filterRepository.getFilters()

        return filtersAvailable
                .closestValue(speciesTankSize)
    }
}

fun List<Filter>.closestValue(value: Int) = minByOrNull { abs(value - it.size) }