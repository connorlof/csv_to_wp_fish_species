package com.loftydevelopment.species_csv_to_wp.calculate

import com.loftydevelopment.species_csv_to_wp.model.Food
import com.loftydevelopment.species_csv_to_wp.model.Species
import com.loftydevelopment.species_csv_to_wp.model.enum.Diet
import com.loftydevelopment.species_csv_to_wp.repository.FoodRepository

object FoodSelector {
    fun selectFoodForSpecies(species: Species, foodRepository: FoodRepository): List<Food> {
        val speciesDietType = species.diet
        val speciesFoodPreferences = species.foodPreferences
        val speciesGroup = species.speciesGroup
        val speciesSize = species.avgAdultSize

        // only check food that fits the diet type
        val foodAvailable = foodRepository.getFood().filter { isFoodProperDiet(it, speciesDietType) }
        val foodPointPairs: MutableList<Pair<Food, Int>> = foodAvailable.map { Pair(it, 0) }.toMutableList()
        val foodPointMap: MutableMap<Food, Int> = foodPointPairs.toMap().toMutableMap()

        for ((food) in foodPointMap) {
            // give a point if species group matches
            if (food.speciesGroups.contains(speciesGroup)) {
                incrementPoint(foodPointMap, food)
            }

            // give a point if food preference matches
            if (speciesFoodPreferences.contains(food.foodType)) {
                incrementPoint(foodPointMap, food)
            }

            // give a point if average size is in food range
            if (food.fishSize.isWithinRange(speciesSize.toDouble())) {
                incrementPoint(foodPointMap, food)
            }
        }

        val outputPairs: MutableList<Pair<Food, Int>> = foodPointMap.toList().toMutableList()

        // Take top three foods with the most points
        outputPairs.sortByDescending { it.second }
        return outputPairs.subList(0, 3).map { it.first }
    }

    fun incrementPoint(map: MutableMap<Food, Int>, key: Food) {
        when (val count = map[key]) {
            null -> map[key] = 1
            else -> map[key] = count + 1
        }
    }
}

fun isFoodProperDiet(food: Food, speciesDiet: Diet): Boolean {
    if (speciesDiet == Diet.OMNIVORE) return true
    if (speciesDiet == Diet.CARNIVORE && food.dietType == Diet.CARNIVORE) return true
    if (speciesDiet == Diet.HERBIVORE && food.dietType == Diet.HERBIVORE) return true

    return false
}