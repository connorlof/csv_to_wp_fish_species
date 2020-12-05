package com.loftydevelopment.species_csv_to_wp.csv

import com.loftydevelopment.species_csv_to_wp.model.Food
import com.loftydevelopment.species_csv_to_wp.model.enum.Diet
import com.loftydevelopment.species_csv_to_wp.model.enum.FoodPreference
import com.loftydevelopment.species_csv_to_wp.model.enum.SpeciesGroup
import com.loftydevelopment.species_csv_to_wp.util.EnumStringUtil
import com.loftydevelopment.species_csv_to_wp.util.Range

class FoodSheet(private val rowList: List<List<String>>) {
    val numRows: Int = rowList.size
    val numColumns: Int = rowList[0].size
    val foodList: List<Food> = parseRows()

    private fun parseRows(): List<Food> {
        val foodList: MutableList<Food> = mutableListOf()

        // Skip header (1st row)
        for (i in 1 until rowList.size) {
            foodList.add(parseRow(rowList[i]))
        }

        return foodList
    }

    private fun parseRow(row: List<String>): Food {
        val fishSizeRange: Range = Range.fromString(row[0])
        val dietType: Diet = Diet.valueOf(EnumStringUtil.toEnumString(row[1]))
        val foodType: FoodPreference = FoodPreference.valueOf(EnumStringUtil.toEnumString(row[2]))
        val species: List<SpeciesGroup> = parseSpeciesList(EnumStringUtil.toEnumString(row[3]))
        val isSpeciesSpecific: Boolean = row[4].toBoolean()
        val name: String = row[5]
        val imageLink: String = row[6]
        val link: String = row[7]

        return Food(fishSizeRange, dietType, foodType, species, isSpeciesSpecific, name, imageLink, link)
    }

    private fun parseSpeciesList(string: String): List<SpeciesGroup> {
        val speciesGroups: MutableList<SpeciesGroup> = mutableListOf()
        val splitSpecies:List<String> = string.split(",")

        for (speciesString in splitSpecies) {
            val cleanedUpString = speciesString.replace("\"", "").trim()
            speciesGroups.add(SpeciesGroup.valueOf(EnumStringUtil.toEnumString(cleanedUpString)))
        }

        return speciesGroups
    }

    override fun toString(): String {
        return rowList.toString()
    }
}