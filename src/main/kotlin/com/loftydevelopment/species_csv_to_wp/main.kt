package com.loftydevelopment.species_csv_to_wp

import com.loftydevelopment.species_csv_to_wp.csv.*
import com.loftydevelopment.species_csv_to_wp.model.Food
import com.loftydevelopment.species_csv_to_wp.repository.*

fun main() {
    // Species sheet reading
    val speciesCsvPath = "C:/Users/conno/Documents/Web/Aquarium API/species.csv"
    val speciesRows = CsvReader.readCsv(speciesCsvPath)
    val speciesSheet = SpeciesSheet(speciesRows)

    val tankRepository = TankSheetRepository()
    val filterRepository = FilterSheetRepository()
    val heaterRepository = HeaterSheetRepository()
    val foodRepository = FoodSheetRepository()

    // Output
    val csvWriter = CsvWriter(foodRepository, tankRepository, filterRepository, heaterRepository)
    csvWriter.writeCsv(csvWriter.generateSpeciesCsvOutput(speciesSheet.species))
}
