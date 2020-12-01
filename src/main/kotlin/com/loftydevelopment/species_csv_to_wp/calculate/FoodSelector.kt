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

        for ((index, foodPointPair) in foodPointPairs.withIndex()) {
            // give a point if species group matches
            if (foodPointPair.first.speciesGroups.contains(speciesGroup)) {
                foodPointPairs[index] = foodPointPair.copy(second = foodPointPair.second + 1)
            }

            // give a point if food preference matches
            if (speciesFoodPreferences.contains(foodPointPair.first.foodType)) {
                foodPointPairs[index] = foodPointPair.copy(second = foodPointPair.second + 1)
            }

            // give a point if average size is in food range
            if (foodPointPair.first.fishSize.isWithinRange(speciesSize.toDouble())) {
                foodPointPairs[index] = foodPointPair.copy(second = foodPointPair.second + 1)
            }
        }

        // Take top three foods with the most points
        foodPointPairs.sortByDescending { it.second }
        return foodPointPairs.subList(0, 2).map { it.first }
    }
}

fun isFoodProperDiet(food: Food, speciesDiet: Diet): Boolean {
    if (speciesDiet == Diet.OMNIVORE) return true
    if (speciesDiet == Diet.CARNIVORE && food.dietType == Diet.CARNIVORE) return true
    if (speciesDiet == Diet.HERBIVORE && food.dietType == Diet.HERBIVORE) return true

    return false
}