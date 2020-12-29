package com.loftydevelopment.species_csv_to_wp.csv

import com.loftydevelopment.species_csv_to_wp.model.Species
import com.loftydevelopment.species_csv_to_wp.model.enum.*
import com.loftydevelopment.species_csv_to_wp.util.EnumStringUtil
import com.loftydevelopment.species_csv_to_wp.util.Quantity
import com.loftydevelopment.species_csv_to_wp.util.Range
import com.loftydevelopment.species_csv_to_wp.util.Ratio

class SpeciesSheet(private val rowList: List<List<String>>) {
    val numRows: Int = rowList.size
    val numColumns: Int = rowList[0].size
    val species: List<Species> = parseRows()

    private fun parseRows(): List<Species> {
        val speciesList: MutableList<Species> = mutableListOf()

        // Skip header (1st row)
        for (i in 1 until rowList.size) {
            speciesList.add(parseRow(rowList[i]))
        }

        return speciesList
    }

    private fun parseRow(row: List<String>): Species {
        val commonName: String = row[0]
        val alternateNames: List<String> = row[1].replace("\"","").split(",")
        val scientificName: String = row[2]
        val speciesGroup: SpeciesGroup = SpeciesGroup.valueOf(EnumStringUtil.toEnumString(row[3]))
        val aggroOverall: Aggressiveness = Aggressiveness.valueOf(EnumStringUtil.toEnumString(row[4].replace("-", "_")))
        val aggroOwnSpecies: Aggressiveness = Aggressiveness.valueOf(EnumStringUtil.toEnumString(row[5].replace("-", "_")))
        val aggroOtherSpecies: Aggressiveness = Aggressiveness.valueOf(EnumStringUtil.toEnumString(row[6].replace("-", "_")))
        val idealQuantity: Quantity = Quantity.fromString(row[7])
        val mfRatio: Ratio = Ratio.fromString(row[8])
        val careLevel: CareLevel = CareLevel.valueOf(EnumStringUtil.toEnumString(row[9]))
        val phRange: Range = Range.fromString(row[10])
        val temperatureRange: Range = Range.fromString(row[11])
        val hardnessRange: Range = Range.fromString(row[12])
        val substrate: Substrate = Substrate.valueOf(EnumStringUtil.toEnumString(row[13]))
        val lightLevel: LightLevel = LightLevel.valueOf(EnumStringUtil.toEnumString(row[14]))
        val waterCurrent: WaterCurrent = WaterCurrent.valueOf(EnumStringUtil.toEnumString(row[15]))
        val isLivePlantCompatible: Boolean = parseYesNoBoolean(row[16])
        val decorationLevel: DecorationLevel = DecorationLevel.valueOf(EnumStringUtil.toEnumString(row[17]))
        val swimmingLevel: SwimmingLevel = SwimmingLevel.valueOf(EnumStringUtil.toEnumString(row[18]))
        val avgAdultSize: Float = row[19].toFloat()
        val maxAdultSize: Float = row[20].toFloat()
        val minTankSize: Int = row[21].toInt()
        val diet: Diet = Diet.valueOf(EnumStringUtil.toEnumString(row[22]))
        val foodPreferences: List<FoodPreference> = parseFoodList(row[23])
        val lifespan: Range = parseLifespan(row[24])
        val speciesAttributes: List<String> = row[25].replace("\"","").split(",")
        val imageUrl: String = row[28]
        val imageSourceUrl: String = row[29]
        val imageLicenseType: String = row[30]
        val imageLicenseUrl: String = row[31]
        val imageAuthor: String = row[32]

        return Species(
                commonName,
                alternateNames,
                scientificName,
                speciesGroup,
                aggroOverall,
                aggroOwnSpecies,
                aggroOtherSpecies,
                idealQuantity,
                mfRatio,
                careLevel,
                phRange,
                temperatureRange,
                hardnessRange,
                substrate,
                lightLevel,
                waterCurrent,
                isLivePlantCompatible,
                decorationLevel,
                swimmingLevel,
                avgAdultSize,
                maxAdultSize,
                minTankSize,
                diet,
                foodPreferences,
                lifespan,
                speciesAttributes,
                imageUrl,
                imageSourceUrl,
                imageLicenseType,
                imageLicenseUrl,
                imageAuthor,
        )
    }

    private fun parseFoodList(string: String): List<FoodPreference> {
        val foodPreferences: MutableList<FoodPreference> = mutableListOf()
        val splitSpecies:List<String> = string.split(",")

        for (speciesString in splitSpecies) {
            val cleanedUpString = speciesString.replace("\"", "").trim()
            foodPreferences.add(FoodPreference.valueOf(EnumStringUtil.toEnumString(cleanedUpString)))
        }

        return foodPreferences
    }

    private fun parseLifespan(string: String): Range {
        val isSingleValue = !string.contains("-")

        return if (isSingleValue) Range(string.toDouble(), string.toDouble())
        else Range.fromString(string)
    }

    private fun parseYesNoBoolean(yesNoString: String): Boolean {
        if (yesNoString.trim().toLowerCase() == "yes") return true

        return false
    }

    override fun toString(): String {
        return rowList.toString()
    }
}